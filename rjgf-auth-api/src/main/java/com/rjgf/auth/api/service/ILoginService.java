package com.rjgf.auth.api.service;


import com.rjgf.auth.api.vo.LoginSysUserVo;

/**
 * 登录接口
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/2
 * @time: 13:55
 */
public interface ILoginService {

    /**
     * 用户登录
     * @param userName
     * @param password
     * @return
     */
    LoginSysUserVo login(String userName, String password) throws Exception;
}
