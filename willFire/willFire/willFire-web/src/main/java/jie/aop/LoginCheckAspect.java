package jie.aop;

import jie.constants.CommonConst;
import jie.entity.User;
import jie.enums.CodeMsg;
import jie.enums.WillFireEnum;
import jie.handle.WillFireLoginException;
import jie.handle.WillFireRuntimeException;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;


@Aspect
@Component
@Order(0)
@Slf4j
public class LoginCheckAspect {

    @Around("@annotation(loginCheck)")
    public Object around(ProceedingJoinPoint joinPoint, LoginCheck loginCheck) throws Throwable {
        String token = WillFireUtil.getToken();
        if (!StringUtils.hasText(token)) {
            throw new WillFireLoginException(CodeMsg.NOT_LOGIN.getMsg());
        }

        User user = (User) WillFireCache.get(token);

        if (user == null) {
            throw new WillFireLoginException(CodeMsg.LOGIN_EXPIRED.getMsg());
        }

        if (token.contains(CommonConst.USER_ACCESS_TOKEN)) {
            if (loginCheck.value() == WillFireEnum.USER_TYPE_ADMIN.getCode() || loginCheck.value() == WillFireEnum.USER_TYPE_DEV.getCode()) {
                throw new WillFireRuntimeException("请输入管理员账号！", 500);
//                return WillFireResult.fail("请输入管理员账号！");
            }
        } else if (token.contains(CommonConst.ADMIN_ACCESS_TOKEN)) {
            log.info("请求IP：" + WillFireUtil.getIpAddr(WillFireUtil.getRequest()));
            if (loginCheck.value() == WillFireEnum.USER_TYPE_ADMIN.getCode() && user.getId().intValue() != CommonConst.ADMIN_USER_ID) {
                throw new WillFireRuntimeException("请输入管理员账号！", 500);
//                return WillFireResult.fail("请输入管理员账号！");
            }
        } else {
            throw new WillFireLoginException(CodeMsg.NOT_LOGIN.getMsg());
        }

        if (loginCheck.value() < user.getUserType()) {
            throw new WillFireRuntimeException("权限不足！", 500);
        }

        //重置过期时间
        String userId = user.getId().toString();
        boolean flag1 = false;
        if (token.contains(CommonConst.USER_ACCESS_TOKEN)) {
            flag1 = WillFireCache.get(CommonConst.USER_TOKEN_INTERVAL + userId) == null;
        } else if (token.contains(CommonConst.ADMIN_ACCESS_TOKEN)) {
            flag1 = WillFireCache.get(CommonConst.ADMIN_TOKEN_INTERVAL + userId) == null;
        }

        if (flag1) {
            synchronized (userId.intern()) {
                boolean flag2 = false;
                if (token.contains(CommonConst.USER_ACCESS_TOKEN)) {
                    flag2 = WillFireCache.get(CommonConst.USER_TOKEN_INTERVAL + userId) == null;
                } else if (token.contains(CommonConst.ADMIN_ACCESS_TOKEN)) {
                    flag2 = WillFireCache.get(CommonConst.ADMIN_TOKEN_INTERVAL + userId) == null;
                }

                if (flag2) {
                    WillFireCache.put(token, user, CommonConst.TOKEN_EXPIRE);
                    if (token.contains(CommonConst.USER_ACCESS_TOKEN)) {
                        WillFireCache.put(CommonConst.USER_TOKEN + userId, token, CommonConst.TOKEN_EXPIRE);
                        WillFireCache.put(CommonConst.USER_TOKEN_INTERVAL + userId, token, CommonConst.TOKEN_INTERVAL);
                    } else if (token.contains(CommonConst.ADMIN_ACCESS_TOKEN)) {
                        WillFireCache.put(CommonConst.ADMIN_TOKEN + userId, token, CommonConst.TOKEN_EXPIRE);
                        WillFireCache.put(CommonConst.ADMIN_TOKEN_INTERVAL + userId, token, CommonConst.TOKEN_INTERVAL);
                    }
                }
            }
        }
        return joinPoint.proceed();
    }
}
