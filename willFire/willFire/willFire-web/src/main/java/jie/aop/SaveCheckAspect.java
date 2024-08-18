package jie.aop;

import jie.constants.CommonConst;
import jie.entity.User;
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

import java.util.concurrent.atomic.AtomicInteger;


@Aspect
@Component
@Order(1)
@Slf4j
public class SaveCheckAspect {

    @Around("@annotation(saveCheck)")
    public Object around(ProceedingJoinPoint joinPoint, SaveCheck saveCheck) throws Throwable {
        boolean flag = false;

        String token = WillFireUtil.getToken();
        if (StringUtils.hasText(token)) {
            User user = (User) WillFireCache.get(token);
            if (user != null) {
                if (user.getId().intValue() == WillFireUtil.getAdminUser().getId().intValue()) {
                    return joinPoint.proceed();
                }

                AtomicInteger atomicInteger = (AtomicInteger) WillFireCache.get(CommonConst.SAVE_COUNT_USER_ID + user.getId().toString());
                if (atomicInteger == null) {
                    atomicInteger = new AtomicInteger();
                    WillFireCache.put(CommonConst.SAVE_COUNT_USER_ID + user.getId().toString(), atomicInteger, CommonConst.SAVE_EXPIRE);
                }
                int userIdCount = atomicInteger.getAndIncrement();
                if (userIdCount >= CommonConst.SAVE_MAX_COUNT) {
                    log.info("用户保存超限：" + user.getId().toString() + "，次数：" + userIdCount);
                    flag = true;
                }
            }
        }

        String ip = WillFireUtil.getIpAddr(WillFireUtil.getRequest());
        AtomicInteger atomic = (AtomicInteger) WillFireCache.get(CommonConst.SAVE_COUNT_IP + ip);
        if (atomic == null) {
            atomic = new AtomicInteger();
            WillFireCache.put(CommonConst.SAVE_COUNT_IP + ip, atomic, CommonConst.SAVE_EXPIRE);
        }
        int ipCount = atomic.getAndIncrement();
        if (ipCount > CommonConst.SAVE_MAX_COUNT) {
            log.info("IP保存超限：" + ip + "，次数：" + ipCount);
            flag = true;
        }

        if (flag) {
            throw new WillFireRuntimeException("今日提交次数已用尽，请一天后再来！");
        }

        return joinPoint.proceed();
    }
}
