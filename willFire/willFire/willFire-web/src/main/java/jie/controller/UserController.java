package jie.controller;

import jie.aop.LoginCheck;
import jie.aop.SaveCheck;
import jie.config.WillFireResult;
import jie.constants.CommonConst;
import jie.service.UserService;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import jie.vo.UserVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/14 上午12:46
 * @注释 用户控制
 */
@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 登录接口
     * @param account
     * @param password
     * @param isAdmin
     * @return
     */
    @PostMapping("/login")
    public WillFireResult<UserVO> login(@RequestParam("account") String account,
                                        @RequestParam("password") String password,
                                        @RequestParam(value = "isAdmin", defaultValue = "false") Boolean isAdmin) {
        UserVO userVO = userService.login(account, password, isAdmin);
//        log.info(userVO.toString());
        return WillFireResult.success(userVO);
    }

    /**
     * 退出登录
     * @return
     */
    @GetMapping("/logout")
    @LoginCheck
    public WillFireResult exit() {
        userService.exit();
        return WillFireResult.success();
    }

    /**
     * 订阅取消专栏
     * @param labelId
     * @param flag
     * @return
     */
    @GetMapping("/subscribe")
    @LoginCheck
    public WillFireResult<UserVO> subscribe(@RequestParam("labelId") Integer labelId, @RequestParam("flag") Boolean flag) {
        WillFireCache.remove(CommonConst.USER_CACHE + WillFireUtil.getUserId().toString());
        UserVO userVO = userService.subscribe(labelId, flag);
        return WillFireResult.success(userVO);
    }

    /**
     * 获取注册、忘记密码的验证码
     * @param place
     * @param flag
     * @return
     */
    @GetMapping("/getCodeForForgetPassword")
    @SaveCheck
    public WillFireResult getCodeForForgetPassword(@RequestParam("place") String place, @RequestParam("flag") Integer flag) {
        userService.getCodeForForgetPassword(place, flag);
        return WillFireResult.success();
//        return userService.getCodeForForgetPassword(place, flag);
    }

    /**
     * 注册
     * @param user
     * @return
     */
    @PostMapping("/regist")
    public WillFireResult<UserVO> regist(@Validated @RequestBody UserVO user) {
        UserVO userVO = userService.regist(user);
        return WillFireResult.success(userVO);
    }

    /**
     * Token登录
     * @param userToken
     * @return
     */
    @PostMapping("/token")
    public WillFireResult<UserVO> login(@RequestParam("userToken") String userToken) {
        UserVO vo = userService.token(userToken);
        return WillFireResult.success(vo);
//        return userService.token(userToken);
    }

    /**
     * 更新用户信息
     */
    @PostMapping("/updateUserInfo")
    @LoginCheck
    public WillFireResult<UserVO> updateUserInfo(@RequestBody UserVO user) {
        WillFireCache.remove(CommonConst.USER_CACHE + WillFireUtil.getUserId().toString());
        return userService.updateUserInfo(user);
    }

    /**
     * 获取验证码
     * <p>
     * 1 手机号
     * 2 邮箱
     */
    @GetMapping("/getCode")
    @LoginCheck
    @SaveCheck
    public WillFireResult getCode(@RequestParam("flag") Integer flag) {
        return userService.getCode(flag);
    }

    /**
     * 绑定手机号或者邮箱
     * <p>
     * 1 手机号
     * 2 邮箱
     */
    @GetMapping("/getCodeForBind")
    @LoginCheck
    @SaveCheck
    public WillFireResult getCodeForBind(@RequestParam("place") String place, @RequestParam("flag") Integer flag) {
        return userService.getCodeForBind(place, flag);
    }

    /**
     * 更新邮箱、手机号、密码
     * <p>
     * 1 手机号
     * 2 邮箱
     * 3 密码：place=老密码&password=新密码
     */
    @PostMapping("/updateSecretInfo")
    @LoginCheck
    public WillFireResult<UserVO> updateSecretInfo(@RequestParam("place") String place, @RequestParam("flag") Integer flag, @RequestParam(value = "code", required = false) String code, @RequestParam("password") String password) {
        WillFireCache.remove(CommonConst.USER_CACHE + WillFireUtil.getUserId().toString());
        return userService.updateSecretInfo(place, flag, code, password);
    }


}
