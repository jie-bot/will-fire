package jie.utils;

import com.alibaba.fastjson.JSON;
import jie.entity.User;
import jie.entity.WebInfo;
import jie.handle.WillFireRuntimeException;
import jie.utils.cache.WillFireCache;
import jie.constants.CommonConst;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.net.InetAddress;
import java.util.List;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午9:04
 * @注释 工具类
 */
public class WillFireUtil {

    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
    }

    public static void checkEMail() {
        User user = (User) WillFireCache.get(WillFireUtil.getToken());
        if (!StringUtils.hasText(user.getEmail())) {
            throw new WillFireRuntimeException("请先绑定邮箱！");
        }
    }

    public static String getToken() {
        String token = WillFireUtil.getRequest().getHeader(CommonConst.TOKEN_HEADER);
        return "null".equals(token) ? null : token;
    }

    public static User getCurrentUser() {
        User user = (User) WillFireCache.get(WillFireUtil.getToken());
        return user;
    }

    public static User getAdminUser() {
        User admin = (User) WillFireCache.get(CommonConst.ADMIN);
        return admin;
    }

    public static Integer getUserId() {
        String token = WillFireUtil.getToken();
        if (!StringUtils.hasText(token)) {
            return null;
        }
        User user = (User) WillFireCache.get(token);
        return user == null ? null : user.getId();
    }

    public static String getUsername() {
        User user = (User) WillFireCache.get(WillFireUtil.getToken());
        return user == null ? null : user.getUsername();
    }

    public static String getRandomAvatar(String key) {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        if (webInfo != null) {
            String randomAvatar = webInfo.getRandomAvatar();
            List<String> randomAvatars = JSON.parseArray(randomAvatar, String.class);
            if (!CollectionUtils.isEmpty(randomAvatars)) {
                if (StringUtils.hasText(key)) {
                    return randomAvatars.get(WillFireUtil.hashLocation(key, randomAvatars.size()));
                } else {
                    String ipAddr = WillFireUtil.getIpAddr(WillFireUtil.getRequest());
                    if (StringUtils.hasText(ipAddr)) {
                        return randomAvatars.get(WillFireUtil.hashLocation(ipAddr, randomAvatars.size()));
                    } else {
                        return randomAvatars.get(0);
                    }
                }
            }
        }
        return null;
    }

    public static String getRandomName(String key) {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        if (webInfo != null) {
            String randomName = webInfo.getRandomName();
            List<String> randomNames = JSON.parseArray(randomName, String.class);
            if (!CollectionUtils.isEmpty(randomNames)) {
                if (StringUtils.hasText(key)) {
                    return randomNames.get(WillFireUtil.hashLocation(key, randomNames.size()));
                } else {
                    String ipAddr = WillFireUtil.getIpAddr(WillFireUtil.getRequest());
                    if (StringUtils.hasText(ipAddr)) {
                        return randomNames.get(WillFireUtil.hashLocation(ipAddr, randomNames.size()));
                    } else {
                        return randomNames.get(0);
                    }
                }
            }
        }
        return null;
    }

    public static String getRandomCover(String key) {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        if (webInfo != null) {
            String randomCover = webInfo.getRandomCover();
            List<String> randomCovers = JSON.parseArray(randomCover, String.class);
            if (!CollectionUtils.isEmpty(randomCovers)) {
                if (StringUtils.hasText(key)) {
                    return randomCovers.get(WillFireUtil.hashLocation(key, randomCovers.size()));
                } else {
                    String ipAddr = WillFireUtil.getIpAddr(WillFireUtil.getRequest());
                    if (StringUtils.hasText(ipAddr)) {
                        return randomCovers.get(WillFireUtil.hashLocation(ipAddr, randomCovers.size()));
                    } else {
                        return randomCovers.get(0);
                    }
                }
            }
        }
        return null;
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress;
        try {
            ipAddress = request.getHeader("x-forwarded-for");
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
                ipAddress = request.getRemoteAddr();
                if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                    // 根据网卡取本机配置的IP
                    ipAddress = InetAddress.getLocalHost().getHostAddress();
                }
            }
            // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
            if (ipAddress != null && ipAddress.length() > 15) {
                if (ipAddress.indexOf(",") > 0) {
                    ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
                }
            }
        } catch (Exception e) {
            ipAddress = null;
        }
        return ipAddress;
    }

    public static int hashLocation(String key, int length) {
        int h = key.hashCode();
        return (h ^ (h >>> 16)) & (length - 1);
    }

    public static String addMd5Password(String password) {
        String newPassword = DigestUtils.md5DigestAsHex(password.getBytes());
        return newPassword;
    }
}
