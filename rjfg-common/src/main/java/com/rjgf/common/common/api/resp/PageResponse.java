package com.rjgf.common.common.api.resp;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PageResponse<T> implements Serializable {

    @ApiModelProperty(value = "当前页码")
    private long current;

    @ApiModelProperty(value = "页数量")
    private long size;

    @ApiModelProperty(value = "数量总数")
    private long total;

    @ApiModelProperty(value = "返回数据")
    private List<T> records;

    @SuppressWarnings("deprecation")
    public PageResponse(IPage<T> iPage) {
        this.current = iPage.getCurrent();
        this.size = iPage.getSize();
        this.total = iPage.getTotal();
        this.records = iPage.getRecords();
    }
}
