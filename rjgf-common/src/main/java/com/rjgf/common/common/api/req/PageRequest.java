package com.rjgf.common.common.api.req;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Predicate;

/**
 * 分页请求类
 *
 * @email: xuliandream@gmail.com
 * @author: xula
 * @date: 2020/2/25
 * @time: 15:36
 */
@ApiModel(value = "分页请求类", description = "分页请求类")
public class PageRequest<T> implements IPage<T> {

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
     * 查询数据列表
     */
    private List<T> records = Collections.emptyList();

    /**
     * 总数
     */
    private long total = 0;

    /**
     * 排序字段信息
     */
    private List<OrderItem> orders = new ArrayList<>();

    /**
     * 自动优化 COUNT SQL
     */
    private boolean optimizeCountSql = true;
    /**
     * 是否进行 count 查询
     */
    private boolean isSearchCount = true;

    public PageRequest() {
    }

    /**
     * 分页构造函数
     *
     * @param current 当前页
     * @param size    每页显示条数
     */
    public PageRequest(long current, long size) {
        this(current, size, 0);
    }

    public PageRequest(long current, long size, long total) {
        this(current, size, total, true);
    }

    public PageRequest(long current, long size, boolean isSearchCount) {
        this(current, size, 0, isSearchCount);
    }

    public PageRequest(long current, long size, long total, boolean isSearchCount) {
        if (current > 1) {
            this.pageNo = current;
        }
        this.pageSize = size;
        this.total = total;
        this.isSearchCount = isSearchCount;
    }

    /**
     * 是否存在上一页
     *
     * @return true / false
     */
    public boolean hasPrevious() {
        return this.pageNo > 1;
    }

    /**
     * 是否存在下一页
     *
     * @return true / false
     */
    public boolean hasNext() {
        return this.pageNo < this.getPages();
    }

    @Override
    public List<T> getRecords() {
        return this.records;
    }


    @Override
    public PageRequest<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    @Override
    public long getTotal() {
        return this.total;
    }


    @Override
    public PageRequest<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    @Override
    public long getSize() {
        return this.pageSize;
    }

    @Override
    public PageRequest<T> setSize(long size) {
        this.pageSize = size;
        return this;
    }

    @Override
    public long getCurrent() {
        return this.pageNo;
    }

    @Override
    public PageRequest<T> setCurrent(long current) {
        this.pageNo = current;
        return this;
    }

    /**
     * 查找 order 中正序排序的字段数组
     *
     * @param filter 过滤器
     * @return 返回正序排列的字段数组
     */
    private String[] mapOrderToArray(Predicate<OrderItem> filter) {
        List<String> columns = new ArrayList<>(orders.size());
        orders.forEach(i -> {
            if (filter.test(i)) {
                columns.add(i.getColumn());
            }
        });
        return columns.toArray(new String[0]);
    }

    /**
     * 移除符合条件的条件
     *
     * @param filter 条件判断
     */
    private void removeOrder(Predicate<OrderItem> filter) {
        for (int i = orders.size() - 1; i >= 0; i--) {
            if (filter.test(orders.get(i))) {
                orders.remove(i);
            }
        }
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：{@link OrderItem#build(String, boolean)}
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageRequest<T> addOrder(OrderItem... items) {
        orders.addAll(Arrays.asList(items));
        return this;
    }

    /**
     * 添加新的排序条件，构造条件可以使用工厂：{@link OrderItem#build(String, boolean)}
     *
     * @param items 条件
     * @return 返回分页参数本身
     */
    public PageRequest<T> addOrder(List<OrderItem> items) {
        orders.addAll(items);
        return this;
    }

    @Override
    public List<OrderItem> orders() {
        return getOrders();
    }

    public List<OrderItem> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderItem> orders) {
        this.orders = orders;
    }

    @Override
    public boolean optimizeCountSql() {
        return optimizeCountSql;
    }

    @Override
    public boolean isSearchCount() {
        if (total < 0) {
            return false;
        }
        return isSearchCount;
    }

    public PageRequest<T> setSearchCount(boolean isSearchCount) {
        this.isSearchCount = isSearchCount;
        return this;
    }

    public PageRequest<T> setOptimizeCountSql(boolean optimizeCountSql) {
        this.optimizeCountSql = optimizeCountSql;
        return this;
    }
}
