package com.rjgf.system.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/7/8
 * @time: 10:35
 */
@Component
@ConfigurationProperties(prefix = "api.path")
@Data
public class ApiPathProperties {

    /**
     * 全部前缀
     */
    String globalPrefix = "api";

    /**
     * 系统相关的前缀
     */
    String sysPrefix = "api/sys";
}
