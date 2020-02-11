
package com.rjgf.common.common.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.enums.ApiErrorCode;
import com.rjgf.common.common.api.resp.PageResponse;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * REST API 返回结果
 *
 * @author xula
 * @since 2020-2-11
 */
@Data
@Accessors(chain = true)
@ApiModel(value = "数据返回对象", description = "数据返回对象")
public class R<T> implements Serializable {

    /**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "业务错误码")
    private long code;

    @ApiModelProperty(value = "结果集")
    private T data;

    @ApiModelProperty(value = "消息")
    private String msg;

    public R() {
        // to do nothing
    }

    public R(ApiCode apiCode) {
        this.code = apiCode.getCode();
        this.msg = apiCode.getMsg();
    }

    public static <T> R<T> ok(T data) {
        ApiCode aec = ApiCode.SUCCESS;
        if (data instanceof Boolean && Boolean.FALSE.equals(data)) {
            aec = ApiCode.FAIL;
        }
        return restResult(data, aec);
    }

    public static <T> R<T> failed(String msg) {
        return restResult(null, ApiCode.FAIL.getCode(), msg);
    }

    public static <T> R<T> failed(ApiCode apiCode) {
        return restResult(null, apiCode);
    }

    public static <T> R<T> failed(int code,String msg) {
        return restResult(null, code, msg);
    }

    public static <T> R<T> failed(ApiCode apiCode,T data) {
        return restResult(data, apiCode);
    }

    public static <T> R<T> restResult(T data, ApiCode apiCode) {
        return restResult(data, apiCode.getCode(), apiCode.getMsg());
    }

    private static <T> R<T> restResult(T data, long code, String msg) {
        R<T> apiResult = new R<>();
        apiResult.setData(data);
        apiResult.setCode(code);
        apiResult.setMsg(msg);
        return apiResult;
    }

    public static <T> R<PageResponse<T>> page(IPage<T> data) {
        ApiCode aec = ApiCode.SUCCESS;
        PageResponse<T> pageResponse = new PageResponse<>(data);
        R<PageResponse<T>> apiResult = new R<>();
        apiResult.setData(pageResponse);
        apiResult.setCode(aec.getCode());
        apiResult.setMsg(aec.getMsg());
        return apiResult;
    }

    public boolean ok() {
        return ApiErrorCode.SUCCESS.getCode() == code;
    }
}
