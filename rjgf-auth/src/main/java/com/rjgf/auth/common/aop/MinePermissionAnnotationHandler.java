package com.rjgf.auth.common.aop;

import com.rjgf.auth.common.annotation.MineRequiresPermissions;
import com.rjgf.auth.util.LoginUtil;
import com.rjgf.auth.util.ShiroUtil;
import com.rjgf.common.constant.SystemConstant;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.aop.AuthorizingAnnotationHandler;
import org.apache.shiro.subject.Subject;

import java.lang.annotation.Annotation;
import java.util.Set;

/**
 * 实现 MinePermission 注解的处理类
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/7/16
 * @time: 10:52
 */
public class MinePermissionAnnotationHandler extends AuthorizingAnnotationHandler {

    /**
     * Default no-argument constructor that ensures this handler looks for
     * {@link com.rjgf.auth.common.annotation.MineRequiresPermissions RequiresPermissions} annotations.
     */
    public MinePermissionAnnotationHandler() {
        super(MineRequiresPermissions.class);
    }

    /**
     * Constructs an <code>AuthorizingAnnotationHandler</code> who processes annotations of the
     * specified type.  Immediately calls <code>super(annotationClass)</code>.
     *
     * @param annotationClass the type of annotation this handler will process.
     */
    public MinePermissionAnnotationHandler(Class<? extends Annotation> annotationClass) {
        super(annotationClass);
    }

    protected String[] getAnnotationValue(Annotation a) {
        MineRequiresPermissions rpAnnotation = (MineRequiresPermissions) a;
        return rpAnnotation.value();
    }

    @Override
    public void assertAuthorized(Annotation a) throws AuthorizationException {
        // 如果是系统用户直接跳过权限的认证
        Set<String> roleCodes = LoginUtil.getRoleCodes();
        if (CollectionUtils.isNotEmpty(roleCodes)) {
            if (roleCodes.contains(SystemConstant.SYSTEM_CODE)) return;
        }
        if (!(a instanceof MineRequiresPermissions)) return;
        MineRequiresPermissions rpAnnotation = (MineRequiresPermissions) a;
        String[] perms = getAnnotationValue(a);
        Subject subject = getSubject();

        if (perms.length == 1) {
            subject.checkPermission(perms[0]);
            return;
        }
        if (Logical.AND.equals(rpAnnotation.logical())) {
            getSubject().checkPermissions(perms);
            return;
        }
        if (Logical.OR.equals(rpAnnotation.logical())) {
            // Avoid processing exceptions unnecessarily - "delay" throwing the exception by calling hasRole first
            boolean hasAtLeastOnePermission = false;
            for (String permission : perms) if (getSubject().isPermitted(permission)) hasAtLeastOnePermission = true;
            // Cause the exception if none of the role match, note that the exception message will be a bit misleading
            if (!hasAtLeastOnePermission) getSubject().checkPermission(perms[0]);
        }
    }
}
