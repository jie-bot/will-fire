package jie.config;

import com.alibaba.fastjson.JSON;
import jie.constants.CommonConst;
import jie.entity.WebInfo;
import jie.enums.CodeMsg;
import jie.utils.cache.WillFireCache;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午9:48
 * @注释
 */
public class WebInfoHandlerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        WebInfo webInfo = (WebInfo) WillFireCache.get(CommonConst.WEB_INFO);
        if (webInfo == null || !webInfo.getStatus()) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JSON.toJSONString(WillFireResult.fail(CodeMsg.SYSTEM_REPAIR.getCode(), CodeMsg.SYSTEM_REPAIR.getMsg())));
            return false;
        } else {
            return true;
        }
    }
}
