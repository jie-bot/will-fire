package jie.config;

import com.alibaba.fastjson.JSON;
import jie.enums.CodeMsg;
import jie.utils.CommonQuery;
import jie.utils.WillFireUtil;
import jie.utils.storage.FileFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/12 下午11:19
 * @注释 记录每个请求的IP地址，过滤请求
 */
@Component
public class WillFireFilter extends OncePerRequestFilter {
    @Autowired
    private CommonQuery commonQuery;

    @Autowired
    private FileFilter fileFilter;

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        if (!"OPTIONS".equals(httpServletRequest.getMethod())) {
            try {
                commonQuery.saveHistory(WillFireUtil.getIpAddr(httpServletRequest));
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (fileFilter.doFilterFile(httpServletRequest, httpServletResponse)) {
                httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
                httpServletResponse.setContentType("application/json;charset=UTF-8");
                httpServletResponse.getWriter().write(JSON.toJSONString(WillFireResult.fail(CodeMsg.PARAMETER_ERROR.getCode(), CodeMsg.PARAMETER_ERROR.getMsg())));
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
