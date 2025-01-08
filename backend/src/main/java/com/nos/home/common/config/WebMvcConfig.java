package com.nos.home.common.config;

import com.nos.home.common.interceptor.ModuleUrlInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {

    private final ModuleUrlInterceptor moduleUrlInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(moduleUrlInterceptor)
                .addPathPatterns("/**")
                .order(1)
                .excludePathPatterns("/static/**", "/error");
    }
}
