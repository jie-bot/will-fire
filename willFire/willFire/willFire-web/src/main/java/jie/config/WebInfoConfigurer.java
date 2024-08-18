package jie.config;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @version 1.0
 * @Author jie
 * @Date 2024/8/13 上午9:44
 * @注释 注册拦截器
 */
@Component
public class WebInfoConfigurer implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebInfoHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/login", "/admin/**", "/webInfo/getWebInfo", "/webInfo/updateWebInfo");
    }
}
