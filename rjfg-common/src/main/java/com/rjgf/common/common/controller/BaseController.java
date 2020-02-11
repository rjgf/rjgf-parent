

package com.rjgf.common.common.controller;
import com.rjgf.common.util.HttpServletRequestUtil;
import com.rjgf.common.util.HttpServletResponseUtil;
import com.rjgf.common.util.jwt.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author geekidea
 * @date 2018-11-08
 */
@Slf4j
public abstract class BaseController {

    /**
     * 获取当前请求
     *
     * @return request
     */
    public HttpServletRequest getRequest() {
        return HttpServletRequestUtil.getRequest();
    }

    /**
     * 获取当前请求
     *
     * @return response
     */
    public HttpServletResponse getResponse() {
        return HttpServletResponseUtil.getRespone();
    }

    /**
     * 获取当前的用户token
     * @return
     */
    public String getToken(){
        return JwtTokenUtil.getToken();
    }
}