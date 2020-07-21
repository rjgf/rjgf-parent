/*
 * Copyright 2019-2029 xula(https://github.com/xula)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rjgf.auth.jwt;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.rjgf.auth.vo.LoginSysUserRedisVo;
import com.rjgf.auth.cache.LoginRedisService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

import static com.rjgf.common.constant.SystemConstant.SYSTEM_CODE;

/**
 * Shiro 授权认证
 *
 * @author xula
 * @date 2019-09-27
 * @since 1.3.0.RELEASE
 **/
@Slf4j
public class JwtRealm extends AuthorizingRealm {

    private LoginRedisService loginRedisService;

    public JwtRealm(LoginRedisService loginRedisService) {
        this.loginRedisService = loginRedisService;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token != null && token instanceof JwtToken;
    }

    /**
     * 授权认证,设置角色/权限信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.debug("doGetAuthorizationInfo principalCollection...");
        // 设置角色/权限信息
        JwtToken jwtToken = (JwtToken) principalCollection.getPrimaryPrincipal();
        // 获取username
        String username = jwtToken.getUsername();
        // 获取当前用户的角色
        // 获取登陆用户角色权限信息
        LoginSysUserRedisVo loginSysUserRedisVo = loginRedisService.getLoginSysUserRedisVo(username);
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        Set<String> roles = loginSysUserRedisVo.getSysRoleCodes();
        // 设置角色
        authorizationInfo.setRoles(roles);
        // 设置权限
        authorizationInfo.setStringPermissions(loginSysUserRedisVo.getPermissionCodes());
        return authorizationInfo;
    }

    /**
     * 登陆认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.debug("doGetAuthenticationInfo authenticationToken...");
        // 校验token
        JwtToken jwtToken = (JwtToken) authenticationToken;
        if (jwtToken == null) {
            throw new AuthenticationException("jwtToken不能为空");
        }
        String salt = jwtToken.getSalt();
        if (StringUtils.isBlank(salt)) {
            throw new AuthenticationException("salt不能为空");
        }
        return new SimpleAuthenticationInfo(
                jwtToken,
                salt,
                getName()
        );

    }

}
