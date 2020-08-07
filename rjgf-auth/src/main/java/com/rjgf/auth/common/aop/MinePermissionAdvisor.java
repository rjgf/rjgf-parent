package com.rjgf.auth.common.aop;

import com.rjgf.auth.common.annotation.MineRequiresPermissions;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/8/7
 * @time: 17:57
 */
public class MinePermissionAdvisor extends AuthorizationAttributeSourceAdvisor {

    /**
     * 添加自定义拦截器
     */
    public MinePermissionAdvisor() {
        setAdvice(new MinePermissionAopInterceptor());
    }


    @SuppressWarnings({"unchecked"})
    @Override
    public boolean matches(Method method, Class targetClass) {
        Method m = method;
        if (targetClass != null) {
            try {
                m = targetClass.getMethod(m.getName(), m.getParameterTypes());
                return this.isFrameAnnotation(m);
            } catch (NoSuchMethodException ignored) {

            }
        }
        return super.matches(method, targetClass);
    }

    private boolean isFrameAnnotation(Method method) {
        return null != AnnotationUtils.findAnnotation(method, MineRequiresPermissions.class);
    }
}
