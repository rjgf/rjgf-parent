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

package com.rjgf.auth.controller;


import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import com.rjgf.auth.vo.LoginSysUserRedisVo;
import com.rjgf.auth.vo.resp.ImgCode;
import com.rjgf.common.common.annotation.ApiRestController;
import com.rjgf.common.common.api.R;
import com.rjgf.common.constant.CommonConstant;
import com.rjgf.common.constant.CommonRedisKey;
import com.rjgf.common.util.jwt.JwtTokenUtil;
import com.rjgf.auth.service.AuthService;
import com.rjgf.auth.util.LoginUtil;
import com.rjgf.auth.vo.req.LoginParam;
import com.rjgf.auth.vo.resp.LoginSysUserTokenVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Base64;
import java.util.concurrent.TimeUnit;

/**
 * 登陆控制器
 * @author xula
 * @date 2020-06-23
 **/
@Api(value = "登陆控制器",tags = "用户登录模块")
@Slf4j
@ApiRestController("/auth")
public class LoginController {

    @Autowired
    private AuthService authService;

    @Autowired
    private RedisTemplate redisTemplate;


    @PostMapping("/login")
    @ApiOperation(value = "登陆", notes = "系统用户登陆", response = LoginSysUserTokenVo.class)
    public R<LoginSysUserTokenVo> login(@Valid @RequestBody LoginParam loginParam) throws Exception {
        LoginSysUserTokenVo loginSysUserTokenVo = authService.login(loginParam);
        return R.ok(loginSysUserTokenVo);
    }


    @ApiOperation(value = "退出", notes = "退出登录")
    @GetMapping("/logout")
    public R logout(HttpServletRequest request) throws Exception {
        authService.logout(request);
        return R.ok("退出成功");
    }

    /**
     * 获取用户信息
     * @return
     */
    @GetMapping("/info")
    @ApiOperation(value = "用户信息", notes = "获取用户详细", response = LoginSysUserRedisVo.class)
    public R<LoginSysUserRedisVo> info() {
        return R.ok(LoginUtil.getLoginSysUserRedisVo());
    }

    /**
     * 获取图片Base64验证码
     */
    @GetMapping("/code")
    @ApiOperation(value = "获取图片Base64验证码", notes = "获取图片Base64验证码", response = ImgCode.class)
    public R<ImgCode> getCode() throws Exception {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(111, 36);
        BufferedImage image = lineCaptcha.getImage();
        String code = lineCaptcha.getCode();
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, CommonConstant.JPEG, outputStream);
        // 将图片转换成base64字符串
        String base64 = Base64.getEncoder().encodeToString(outputStream.toByteArray());
        // 生成当前验证码会话token
        String uuid = IdUtil.simpleUUID();
        ImgCode imgCode = new ImgCode();
        imgCode.setImage(CommonConstant.BASE64_PREFIX + base64);
        imgCode.setUuid(uuid);
        // 缓存到Redis
        redisTemplate.opsForValue().set(String.format(CommonRedisKey.VERIFY_CODE, uuid), code, 5, TimeUnit.MINUTES);
        return R.ok(imgCode);
    }


}

