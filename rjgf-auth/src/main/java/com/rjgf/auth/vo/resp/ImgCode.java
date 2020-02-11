package com.rjgf.auth.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/11
 * @time: 10:32
 */
@Data
@Accessors(chain = true)
@ApiModel("图片验证码 Vo")
public class ImgCode {

    @ApiModelProperty("base64")
    private String image;


    @ApiModelProperty("验证码uuid")
    private String uuid;
}
