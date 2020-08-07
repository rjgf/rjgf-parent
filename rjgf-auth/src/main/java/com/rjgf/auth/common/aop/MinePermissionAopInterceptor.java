package com.rjgf.auth.common.aop;

import org.apache.shiro.spring.aop.SpringAnnotationResolver;
import org.apache.shiro.spring.security.interceptor.AopAllianceAnnotationsAuthorizingMethodInterceptor;

/**
 * 自定义验证aop拦截器
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/8/7
 * @time: 17:52
 */
public class MinePermissionAopInterceptor extends AopAllianceAnnotationsAuthorizingMethodInterceptor {

    public MinePermissionAopInterceptor() {
        super();
        // 添加自定义的注解拦截器
        this.methodInterceptors.add(new MinePermissionAnnotationMethodInterceptor(new SpringAnnotationResolver()));
    }
}
