package jie.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.dao.UserMapper;
import jie.entity.User;
import jie.entity.WebInfo;
import jie.entity.WeiYan;
import jie.enums.CodeMsg;
import jie.enums.WillFireEnum;
import jie.handle.WillFireRuntimeException;
import jie.service.UserService;
import jie.service.WeiYanService;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import jie.utils.mail.MailUtil;
import jie.vo.BaseRequestVO;
import jie.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午10:27
 * @注释
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private MailUtil mailUtil;

    @Autowired
    private WeiYanService weiYanService;


    /**
     * 用户登录
     * @param account
     * @param password
     * @param isAdmin
     * @return
     */
    @Override
    public UserVO login(String account, String password, Boolean isAdmin) {
        password = new String(SecureUtil.aes(CommonConst.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).decrypt(password));

        User one = lambdaQuery().and(wrapper -> wrapper
                        .eq(User::getUsername, account)
                        .or()
                        .eq(User::getEmail, account)
                        .or()
                        .eq(User::getPhoneNumber, account))
                .eq(User::getPassword, DigestUtils.md5DigestAsHex(password.getBytes()))
                .one();

//        log.info("加密之后" + WillFireUtil.addMd5Password(password));

        if (one == null) {
            throw new WillFireRuntimeException(CodeMsg.USERNAME_PASSWPRD_ERROR);
        }

        // 用户是否被冻结
        if (!one.getUserStatus()) {
            throw new WillFireRuntimeException(CodeMsg.USER_STATUS_ERROR);
        }

        // 生成Token
        String adminToken = "";
        String userToken = "";

        if (isAdmin) {
            // 不是站长和管理员
            if (one.getUserType() != WillFireEnum.USER_TYPE_ADMIN.getCode() && one.getUserType() != WillFireEnum.USER_TYPE_DEV.getCode()) {
                throw new WillFireRuntimeException(CodeMsg.NOT_ADMIN_ACCOUNT);
            }
            // 生成Token
            if (WillFireCache.get(CommonConst.ADMIN_TOKEN + one.getId()) != null) {
                adminToken = (String) WillFireCache.get(CommonConst.ADMIN_TOKEN + one.getId());
            }
        } else {
            if (WillFireCache.get(CommonConst.USER_TOKEN + one.getId()) != null) {
                userToken = (String) WillFireCache.get(CommonConst.USER_TOKEN + one.getId());
            }
        }

        if (isAdmin && !StringUtils.hasText(adminToken)) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            adminToken = CommonConst.ADMIN_ACCESS_TOKEN + uuid;
            WillFireCache.put(adminToken, one, CommonConst.TOKEN_EXPIRE);
            WillFireCache.put(CommonConst.ADMIN_TOKEN + one.getId(), adminToken, CommonConst.TOKEN_EXPIRE);
        } else if (!isAdmin && !StringUtils.hasText(userToken)) {
            String uuid = UUID.randomUUID().toString().replaceAll("-", "");
            userToken = CommonConst.USER_ACCESS_TOKEN + uuid;
            WillFireCache.put(userToken, one, CommonConst.TOKEN_EXPIRE);
            WillFireCache.put(CommonConst.USER_TOKEN + one.getId(), userToken, CommonConst.TOKEN_EXPIRE);
        }

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(one, userVO);
        userVO.setPassword(null);
        if (isAdmin && one.getUserType() == WillFireEnum.USER_TYPE_ADMIN.getCode()) {
            userVO.setIsBoss(true);
        }

        if (isAdmin) {
            userVO.setAccessToken(adminToken);
        } else {
            userVO.setAccessToken(userToken);
        }
        return userVO;
    }

    /**
     * 退出登录
     */
    @Override
    public void exit() {
        String token = WillFireUtil.getToken();
        Integer userId = WillFireUtil.getUserId();
        if (token.contains(CommonConst.USER_ACCESS_TOKEN)) {
            WillFireCache.remove(CommonConst.USER_TOKEN + userId);
//            TioWebsocketStarter tioWebsocketStarter = TioUtil.getTio();
//            if (tioWebsocketStarter != null) {
//                Tio.removeUser(tioWebsocketStarter.getServerTioConfig(), String.valueOf(userId), "remove user");
//            }
        } else if (token.contains(CommonConst.ADMIN_ACCESS_TOKEN)) {
            WillFireCache.remove(CommonConst.ADMIN_TOKEN + userId);
        }
        WillFireCache.remove(token);
    }

    /**
     * 获取用户列表
     * @param baseRequestVO
     * @return
     */
    @Override
    public Page listUser(BaseRequestVO baseRequestVO) {
        LambdaQueryChainWrapper<User> lambdaQuery = lambdaQuery();

        if (baseRequestVO.getUserStatus() != null) {
            lambdaQuery.eq(User::getUserStatus, baseRequestVO.getUserStatus());
        }

        if (baseRequestVO.getUserType() != null) {
            lambdaQuery.eq(User::getUserType, baseRequestVO.getUserType());
        }

        if (StringUtils.hasText(baseRequestVO.getSearchKey())) {
            lambdaQuery.and(lq -> lq.like(User::getUsername, baseRequestVO.getSearchKey())
                    .or()
                    .like(User::getPhoneNumber, baseRequestVO.getSearchKey())
                    .or()
                    .like(User::getEmail, baseRequestVO.getSearchKey()));
        }

        lambdaQuery.orderByDesc(User::getCreateTime).page(baseRequestVO);

        List<User> records = baseRequestVO.getRecords();
        if (!CollectionUtils.isEmpty(records)) {
            records.forEach(u -> {
                u.setPassword(null);
                u.setOpenId(null);
            });
        }
        return baseRequestVO;
    }

    /**
     * 订阅取消专栏
     * @param labelId
     * @param flag
     * @return
     */
    @Override
    public UserVO subscribe(Integer labelId, Boolean flag) {
        UserVO userVO = null;
        User one = lambdaQuery().eq(User::getId, WillFireUtil.getUserId()).one();
        List<Integer> sub = JSON.parseArray(one.getSubscribe(), Integer.class);
        if (sub == null) sub = new ArrayList<>();
        if (flag) {
            // 订阅专栏
            if (!sub.contains(labelId)) {
                sub.add(labelId);
                User user = new User();
                user.setId(one.getId());
                user.setSubscribe(JSON.toJSONString(sub));
                updateById(user);

                userVO = new UserVO();
                BeanUtils.copyProperties(one, userVO);
                userVO.setPassword(null);
                userVO.setSubscribe(user.getSubscribe());
                userVO.setAccessToken(WillFireUtil.getToken());
            }
        } else {
            if (sub.contains(labelId)) {
                sub.remove(labelId);
                User user = new User();
                user.setId(one.getId());
                user.setSubscribe(JSON.toJSONString(sub));
                updateById(user);

                userVO = new UserVO();
                BeanUtils.copyProperties(one, userVO);
                userVO.setPassword(null);
                userVO.setSubscribe(user.getSubscribe());
                userVO.setAccessToken(WillFireUtil.getToken());
            }
        }
        return userVO;
    }

    /**
     * 生成验证码
     * @param place
     * @param flag
     */
    @Override
    public void getCodeForForgetPassword(String place, Integer flag) {
        int i = new Random().nextInt(900000) + 100000;
        if (flag == 1) {
            log.info(place + "---" + "手机验证码---" + i);
        } else if (flag == 2) {
            log.info(place + "---" + "邮箱验证码---" + i);
            List<String> mail = new ArrayList<>();
            mail.add(place);
            String text = getCodeMail(i);
            WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);

            AtomicInteger count = (AtomicInteger) WillFireCache.get(CommonConst.CODE_MAIL + mail.get(0));
            if (count == null || count.get() < CommonConst.CODE_MAIL_COUNT) {
                mailUtil.sendMailMessage(mail, "您有一封来自" + (webInfo == null ? "POETIZE" : webInfo.getWebName()) + "的回执！", text);
                if (count == null) {
                    WillFireCache.put(CommonConst.CODE_MAIL + mail.get(0), new AtomicInteger(1), CommonConst.CODE_EXPIRE);
                } else {
                    count.incrementAndGet();
                }
            } else {
                throw new WillFireRuntimeException("验证码发送次数过多，请明天再试", 500);
//                return WillFireResult.fail("验证码发送次数过多，请明天再试！");
            }
        }
        WillFireCache.put(CommonConst.FORGET_PASSWORD + place + "_" + flag, Integer.valueOf(i), 300);
    }

    /**
     * 注册
     *
     * @param user
     * @return
     */
    @Override
    public UserVO regist(UserVO user) {
        String regex = "\\d{11}";
        if (user.getUsername().matches(regex)) {
            throw new WillFireRuntimeException("用户名不能为11位数字！", 500);
//            return WillFireResult.fail("用户名不能为11位数字！");
        }

        if (user.getUsername().contains("@")) {
            throw new WillFireRuntimeException("用户名不能包含@！", 500);
//            return WillFireResult.fail("用户名不能包含@！");
        }

        if (StringUtils.hasText(user.getPhoneNumber()) && StringUtils.hasText(user.getEmail())) {
            throw new WillFireRuntimeException("手机号与邮箱只能选择其中一个！", 500);
//            return WillFireResult.fail("手机号与邮箱只能选择其中一个！");
        }

        if (StringUtils.hasText(user.getPhoneNumber())) {
            Integer codeCache = (Integer) WillFireCache.get(CommonConst.FORGET_PASSWORD + user.getPhoneNumber() + "_1");
            if (codeCache == null || codeCache != Integer.parseInt(user.getCode())) {
                throw new WillFireRuntimeException("验证码错误！", 500);
//                return WillFireResult.fail("验证码错误！");
            }
            WillFireCache.remove(CommonConst.FORGET_PASSWORD + user.getPhoneNumber() + "_1");
        } else if (StringUtils.hasText(user.getEmail())) {
            Integer codeCache = (Integer) WillFireCache.get(CommonConst.FORGET_PASSWORD + user.getEmail() + "_2");
            if (codeCache == null || codeCache != Integer.parseInt(user.getCode())) {
                throw new WillFireRuntimeException("验证码错误！", 500);
//                return WillFireResult.fail("验证码错误！");
            }
            WillFireCache.remove(CommonConst.FORGET_PASSWORD + user.getEmail() + "_2");
        } else {
            throw new WillFireRuntimeException("请输入邮箱或者手机号！", 500);
//            return WillFireResult.fail("请输入邮箱或手机号！");
        }


        user.setPassword(new String(SecureUtil.aes(CommonConst.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).decrypt(user.getPassword())));

        Integer count = lambdaQuery().eq(User::getUsername, user.getUsername()).count();
        if (count != 0) {
            throw new WillFireRuntimeException("用户名重复！", 500);
//            return WillFireResult.fail("用户名重复！");
        }
        if (StringUtils.hasText(user.getPhoneNumber())) {
            Integer phoneNumberCount = lambdaQuery().eq(User::getPhoneNumber, user.getPhoneNumber()).count();
            if (phoneNumberCount != 0) {
                throw new WillFireRuntimeException("手机号重复！", 500);
//                return WillFireResult.fail("手机号重复！");
            }
        } else if (StringUtils.hasText(user.getEmail())) {
            Integer emailCount = lambdaQuery().eq(User::getEmail, user.getEmail()).count();
            if (emailCount != 0) {
                throw new WillFireRuntimeException("邮箱重复！", 500);
//                return WillFireResult.fail("邮箱重复！");
            }
        }

        User u = new User();
        u.setUsername(user.getUsername());
        u.setPhoneNumber(user.getPhoneNumber());
        u.setEmail(user.getEmail());
        u.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
        u.setAvatar(WillFireUtil.getRandomAvatar(null));
        save(u);

        User one = lambdaQuery().eq(User::getId, u.getId()).one();

        String userToken = CommonConst.USER_ACCESS_TOKEN + UUID.randomUUID().toString().replaceAll("-", "");
        WillFireCache.put(userToken, one, CommonConst.TOKEN_EXPIRE);
        WillFireCache.put(CommonConst.USER_TOKEN + one.getId(), userToken, CommonConst.TOKEN_EXPIRE);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(one, userVO);
        userVO.setPassword(null);
        userVO.setAccessToken(userToken);

        WeiYan weiYan = new WeiYan();
        weiYan.setUserId(one.getId());
        weiYan.setContent("到此一游");
        weiYan.setType(CommonConst.WEIYAN_TYPE_FRIEND);
        weiYan.setIsPublic(Boolean.TRUE);
        weiYanService.save(weiYan);

//        ImChatGroupUser imChatGroupUser = new ImChatGroupUser();
//        imChatGroupUser.setGroupId(ImConfigConst.DEFAULT_GROUP_ID);
//        imChatGroupUser.setUserId(one.getId());
//        imChatGroupUser.setUserStatus(ImConfigConst.GROUP_USER_STATUS_PASS);
//        imChatGroupUserMapper.insert(imChatGroupUser);
//
//        ImChatUserFriend imChatUser = new ImChatUserFriend();
//        imChatUser.setUserId(one.getId());
//        imChatUser.setFriendId(WillFireUtil.getAdminUser().getId());
//        imChatUser.setRemark("站长");
//        imChatUser.setFriendStatus(ImConfigConst.FRIEND_STATUS_PASS);
//        imChatUserFriendMapper.insert(imChatUser);
//
//        ImChatUserFriend imChatFriend = new ImChatUserFriend();
//        imChatFriend.setUserId(WillFireUtil.getAdminUser().getId());
//        imChatFriend.setFriendId(one.getId());
//        imChatFriend.setFriendStatus(ImConfigConst.FRIEND_STATUS_PASS);
//        imChatUserFriendMapper.insert(imChatFriend);
        return userVO;
//        return WillFireResult.success(userVO);
    }

    /**
     * token登录
     * @param userToken
     * @return
     */
    @Override
    public UserVO token(String userToken) {
        userToken = new String(SecureUtil.aes(CommonConst.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).decrypt(userToken));
        if (!StringUtils.hasText(userToken)) {
            throw new WillFireRuntimeException("未登陆，请登陆后再进行操作！", 500);
        }
        User user = (User) WillFireCache.get(userToken);
        if (user == null) {
            throw new WillFireRuntimeException("登录已过期，请重新登陆！", 500);
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setPassword(null);

        userVO.setAccessToken(userToken);
        return userVO;
    }

    @Override
    public WillFireResult getCodeForBind(String place, Integer flag) {
        int i = new Random().nextInt(900000) + 100000;
        if (flag == 1) {
            log.info(place + "---" + "手机验证码---" + i);
        } else if (flag == 2) {
            log.info(place + "---" + "邮箱验证码---" + i);
            List<String> mail = new ArrayList<>();
            mail.add(place);
            String text = getCodeMail(i);
            WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);

            AtomicInteger count = (AtomicInteger) WillFireCache.get(CommonConst.CODE_MAIL + mail.get(0));
            if (count == null || count.get() < CommonConst.CODE_MAIL_COUNT) {
                mailUtil.sendMailMessage(mail, "您有一封来自" + (webInfo == null ? "POETIZE" : webInfo.getWebName()) + "的回执！", text);
                if (count == null) {
                    WillFireCache.put(CommonConst.CODE_MAIL + mail.get(0), new AtomicInteger(1), CommonConst.CODE_EXPIRE);
                } else {
                    count.incrementAndGet();
                }
            } else {
                return WillFireResult.fail("验证码发送次数过多，请明天再试！");
            }
        }
        WillFireCache.put(CommonConst.USER_CODE + WillFireUtil.getUserId() + "_" + place + "_" + flag, Integer.valueOf(i), 300);
        return WillFireResult.success();
    }

    @Override
    public WillFireResult<UserVO> updateUserInfo(UserVO user) {
        if (StringUtils.hasText(user.getUsername())) {
            String regex = "\\d{11}";
            if (user.getUsername().matches(regex)) {
                return WillFireResult.fail("用户名不能为11位数字！");
            }

            if (user.getUsername().contains("@")) {
                return WillFireResult.fail("用户名不能包含@！");
            }

            Integer count = lambdaQuery().eq(User::getUsername, user.getUsername()).ne(User::getId, WillFireUtil.getUserId()).count();
            if (count != 0) {
                return WillFireResult.fail("用户名重复！");
            }
        }
        User u = new User();
        u.setId(WillFireUtil.getUserId());
        u.setUsername(user.getUsername());
        u.setAvatar(user.getAvatar());
        u.setGender(user.getGender());
        u.setIntroduction(user.getIntroduction());
        updateById(u);
        User one = lambdaQuery().eq(User::getId, u.getId()).one();
        WillFireCache.put(WillFireUtil.getToken(), one, CommonConst.TOKEN_EXPIRE);
        WillFireCache.put(CommonConst.USER_TOKEN + one.getId(), WillFireUtil.getToken(), CommonConst.TOKEN_EXPIRE);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(one, userVO);
        userVO.setPassword(null);
        userVO.setAccessToken(WillFireUtil.getToken());
        return WillFireResult.success(userVO);
    }

    @Override
    public WillFireResult getCode(Integer flag) {
        User user = WillFireUtil.getCurrentUser();
        int i = new Random().nextInt(900000) + 100000;
        if (flag == 1) {
            if (!StringUtils.hasText(user.getPhoneNumber())) {
                return WillFireResult.fail("请先绑定手机号！");
            }

            log.info(user.getId() + "---" + user.getUsername() + "---" + "手机验证码---" + i);
        } else if (flag == 2) {
            if (!StringUtils.hasText(user.getEmail())) {
                return WillFireResult.fail("请先绑定邮箱！");
            }

            log.info(user.getId() + "---" + user.getUsername() + "---" + "邮箱验证码---" + i);

            List<String> mail = new ArrayList<>();
            mail.add(user.getEmail());
            String text = getCodeMail(i);
            WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);

            AtomicInteger count = (AtomicInteger) WillFireCache.get(CommonConst.CODE_MAIL + mail.get(0));
            if (count == null || count.get() < CommonConst.CODE_MAIL_COUNT) {
                mailUtil.sendMailMessage(mail, "您有一封来自" + (webInfo == null ? "POETIZE" : webInfo.getWebName()) + "的回执！", text);
                if (count == null) {
                    WillFireCache.put(CommonConst.CODE_MAIL + mail.get(0), new AtomicInteger(1), CommonConst.CODE_EXPIRE);
                } else {
                    count.incrementAndGet();
                }
            } else {
                return WillFireResult.fail("验证码发送次数过多，请明天再试！");
            }
        }
        WillFireCache.put(CommonConst.USER_CODE + WillFireUtil.getUserId() + "_" + flag, Integer.valueOf(i), 300);
        return WillFireResult.success();
    }

    @Override
    public WillFireResult<UserVO> updateSecretInfo(String place, Integer flag, String code, String password) {
        password = new String(SecureUtil.aes(CommonConst.CRYPOTJS_KEY.getBytes(StandardCharsets.UTF_8)).decrypt(password));

        User user = WillFireUtil.getCurrentUser();
        if ((flag == 1 || flag == 2) && !DigestUtils.md5DigestAsHex(password.getBytes()).equals(user.getPassword())) {
            return WillFireResult.fail("密码错误！");
        }
        if ((flag == 1 || flag == 2) && !StringUtils.hasText(code)) {
            return WillFireResult.fail("请输入验证码！");
        }
        User updateUser = new User();
        updateUser.setId(user.getId());
        if (flag == 1) {
            Integer count = lambdaQuery().eq(User::getPhoneNumber, place).count();
            if (count != 0) {
                return WillFireResult.fail("手机号重复！");
            }
            Integer codeCache = (Integer) WillFireCache.get(CommonConst.USER_CODE + WillFireUtil.getUserId() + "_" + place + "_" + flag);
            if (codeCache != null && codeCache.intValue() == Integer.parseInt(code)) {

                WillFireCache.remove(CommonConst.USER_CODE + WillFireUtil.getUserId() + "_" + place + "_" + flag);

                updateUser.setPhoneNumber(place);
            } else {
                return WillFireResult.fail("验证码错误！");
            }

        } else if (flag == 2) {
            Integer count = lambdaQuery().eq(User::getEmail, place).count();
            if (count != 0) {
                return WillFireResult.fail("邮箱重复！");
            }
            Integer codeCache = (Integer) WillFireCache.get(CommonConst.USER_CODE + WillFireUtil.getUserId() + "_" + place + "_" + flag);
            if (codeCache != null && codeCache.intValue() == Integer.parseInt(code)) {

                WillFireCache.remove(CommonConst.USER_CODE + WillFireUtil.getUserId() + "_" + place + "_" + flag);

                updateUser.setEmail(place);
            } else {
                return WillFireResult.fail("验证码错误！");
            }
        } else if (flag == 3) {
            if (DigestUtils.md5DigestAsHex(place.getBytes()).equals(user.getPassword())) {
                updateUser.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));
            } else {
                return WillFireResult.fail("密码错误！");
            }
        }
        updateById(updateUser);

        User one = lambdaQuery().eq(User::getId, user.getId()).one();
        WillFireCache.put(WillFireUtil.getToken(), one, CommonConst.TOKEN_EXPIRE);
        WillFireCache.put(CommonConst.USER_TOKEN + one.getId(), WillFireUtil.getToken(), CommonConst.TOKEN_EXPIRE);

        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(one, userVO);
        userVO.setPassword(null);
        return WillFireResult.success(userVO);
    }

    /**
     * 生成邮箱格式
     * @param i
     * @return
     */
    private String getCodeMail(int i) {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        String webName = (webInfo == null ? "火の意志" : webInfo.getWebName());
        return String.format(mailUtil.getMailText(),
                webName,
                String.format(MailUtil.imMail, WillFireUtil.getAdminUser().getUsername()),
                WillFireUtil.getAdminUser().getUsername(),
                String.format("您好，您的验证码是 %6d", i),
                "",
                webName);
    }
}
