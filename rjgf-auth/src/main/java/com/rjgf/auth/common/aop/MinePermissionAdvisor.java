package com.rjgf.auth.common.aop;

import com.rjgf.auth.common.annotation.MineRequiresPermissions;
import com.rjgf.common.core.properties.ShiroProperties;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Method;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/8/7
 * @time: 17:57
 */
public class MinePermissionAdvisor extends AuthorizationAttributeSourceAdvisor {

    private ShiroProperties shiroProperties;
    /**
     * 添加自定义拦截器
     */
    public MinePermissionAdvisor() {
        super();
        setAdvice(new MinePermissionAopInterceptor());
    }

    public void setShiroProperties(ShiroProperties shiroProperties) {
        this.shiroProperties = shiroProperties;
    }

    @SuppressWarnings({"unchecked"})
    @Override
    public boolean matches(Method method, Class targetClass) {
        // 如果没有启用shiro相关注解，则全部匹配不成功
        if (!shiroProperties.isEnableAnnotation()) {
            return false;
        }
        Method m = method;
        if (targetClass != null) {
            try {
                m = targetClass.getMethod(m.getName(), m.getParameterTypes());
                return this.isFrameAnnotation(m);
            } catch (NoSuchMethodException ignored) {

            }
        }
        return super.matches(m, targetClass);
    }

    private boolean isFrameAnnotation(Method method) {
        return null != AnnotationUtils.findAnnotation(method, MineRequiresPermissions.class);
    }
}
