package jie.utils.storage;

import jie.constants.CommonConst;
import jie.entity.User;
import jie.utils.WillFireUtil;
import jie.utils.cache.WillFireCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午9:02
 * @注释 文件过滤器，限制文件上传的频率
 */

@Component
@Slf4j
public class FileFilter {

    private final AntPathMatcher matcher = new AntPathMatcher();

    public boolean doFilterFile(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        if (matcher.match("/resource/upload", httpServletRequest.getRequestURI())) {
            String token = WillFireUtil.getToken();
            if (StringUtils.hasText(token)) {
                User user = (User) WillFireCache.get(token);

                if (user != null) {
                    if (user.getId().intValue() == WillFireUtil.getAdminUser().getId().intValue()) {
                        return false;
                    }

                    AtomicInteger atomicInteger = (AtomicInteger) WillFireCache.get(CommonConst.SAVE_COUNT_USER_ID + user.getId().toString());
                    if (atomicInteger == null) {
                        atomicInteger = new AtomicInteger();
                        WillFireCache.put(CommonConst.SAVE_COUNT_USER_ID + user.getId().toString(), atomicInteger, CommonConst.SAVE_EXPIRE);
                    }
                    int userIdCount = atomicInteger.getAndIncrement();

                    String ip = WillFireUtil.getIpAddr(WillFireUtil.getRequest());
                    AtomicInteger atomic = (AtomicInteger) WillFireCache.get(CommonConst.SAVE_COUNT_IP + ip);
                    if (atomic == null) {
                        atomic = new AtomicInteger();
                        WillFireCache.put(CommonConst.SAVE_COUNT_IP + ip, atomic, CommonConst.SAVE_EXPIRE);
                    }
                    int ipCount = atomic.getAndIncrement();

                    return userIdCount >= CommonConst.SAVE_MAX_COUNT || ipCount >= CommonConst.SAVE_MAX_COUNT;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
