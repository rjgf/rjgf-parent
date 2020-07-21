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

package com.rjgf.system.vo.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 修改密码参数
 *
 * @author xula
 * @date 2019-10-27
 **/
@Data
@Accessors(chain = true)
public class UpdatePasswordParam {

    @ApiModelProperty("用户id")
    @NotNull(message = "用户id不能为空")
    private Long userId;

    @ApiModelProperty("原密码")
    @NotEmpty(message = "原密码不能为空")
    private String oldPassword;

    @ApiModelProperty("新密码")
    @NotEmpty(message = "新密码不能为空")
    private String newPassword;

    @ApiModelProperty("新密码")
    @NotEmpty(message = "确认密码不能为空")
    private String confirmPassword;

}
