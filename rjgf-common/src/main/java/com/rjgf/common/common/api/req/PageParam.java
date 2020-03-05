package com.rjgf.common.common.api.req;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页请求类
 *
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/25
 * @time: 15:36
 */
public class PageParam implements Serializable {

    /**
     * 当前页
     */
    @Getter
    @Setter
    @ApiModelProperty(value = "当前页码", required = true)
    private long pageNo = 1;

    /**
     * 每页显示的数量
     */
    @Getter
    @Setter
    @ApiModelProperty(value = "每页显示的数量", required = true)
    private long pageSize = 10;

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();
}
