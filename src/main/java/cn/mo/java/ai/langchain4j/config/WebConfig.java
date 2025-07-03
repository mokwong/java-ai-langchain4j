package cn.mo.java.ai.langchain4j.config;

import cn.mo.java.ai.langchain4j.session.WebSessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author mo
 * @Description 将拦截器添加到 Spring MVC 的拦截器链中
 * @createTime 2025年07月03日 15:20
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new WebSessionInterceptor())
                .addPathPatterns("/**");       // 拦截所有请求路径
    }

}
