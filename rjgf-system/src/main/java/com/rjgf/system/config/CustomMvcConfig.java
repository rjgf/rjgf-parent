package com.rjgf.system.config;

import com.rjgf.common.common.annotation.ApiRestController;
import com.rjgf.system.config.properties.ApiPathProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.PathMatchConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/7/8
 * @time: 10:33
 */
@Configuration
public class CustomMvcConfig implements WebMvcConfigurer {

    @Autowired
    private ApiPathProperties apiPathProperties;

    /**
     * 设置路由前缀
     * @param configurer
     */
    @Override
    public void configurePathMatch(PathMatchConfigurer configurer) {
        configurer
                .addPathPrefix(apiPathProperties.getGlobalPrefix(),c -> c.isAnnotationPresent(ApiRestController.class));
    }
}
