package com.infosky.framework.web;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页Bean
 * @author n004881
 * createTime 2014-6-19
 */
public class PageResult<T> implements Serializable {

    /**
     * 注释内容
     */
    private static final long serialVersionUID = -693271012656238669L;

    /**
     * 该参数用于验证分页请求合法性 返回的值必须与传递的值相同
     */
    private int draw;

    /**
     * 起始记录数
     */
    private long start;

    /**
     * 分页大小
     */
    private int length = 10;

    /**
     * 总数
     */
    private long recordsTotal;

    /**
     * 已过滤的数
     */
    private long recordsFiltered;

    /**
     * 返回的列表
     */
    private List<T> data = new ArrayList<T>(0);

    public PageResult() {
    }

    public PageResult(int draw, long recordsTotal, List<T> data) {
        this(draw, recordsTotal, recordsTotal, data);
    }

    public PageResult(int draw, long recordsTotal, long recordsFiltered, List<T> data) {
        this.draw = draw;
        this.recordsFiltered = recordsFiltered;
        this.recordsTotal = recordsTotal;
        this.data = data;
    }

    public int getDraw() {
        return draw;
    }

    public void setDraw(int draw) {
        this.draw = draw;
    }

    public long getRecordsTotal() {
        return recordsTotal;
    }

    public void setRecordsTotal(long recordsTotal) {
        this.recordsTotal = recordsTotal;
        this.recordsFiltered = recordsTotal;
    }

    public long getRecordsFiltered() {
        return recordsFiltered;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
