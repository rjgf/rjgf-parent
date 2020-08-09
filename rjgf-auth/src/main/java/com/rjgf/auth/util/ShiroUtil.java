package com.rjgf.auth.util;

import com.rjgf.common.core.properties.ShiroProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/8/9
 * @time: 17:49
 */
@Component
@Slf4j
public class ShiroUtil {

    private static ShiroProperties shiroProperties;

    public ShiroUtil(ShiroProperties shiroProperties) {
        this.shiroProperties = shiroProperties;
    }

    /**
     * 判断shiro 自定义注解 是否启用
     * @return
     */
    public static boolean isEnableShiroAnnotation() {
        return shiroProperties.isEnableAnnotation();
    }
}
