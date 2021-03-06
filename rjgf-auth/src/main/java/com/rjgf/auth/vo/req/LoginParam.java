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

package com.rjgf.auth.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * 登录参数
 *
 * @author xula
 * @date 2019-05-15
 **/
@Data
@ApiModel("登录参数")
public class LoginParam {

    @NotBlank(message = "请输入账号")
    @ApiModelProperty("账号")
    private String userName;

    @NotBlank(message = "请输入密码")
    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码 uuid")
    private String uuid;

    @ApiModelProperty("验证码")
    private String code;

}
