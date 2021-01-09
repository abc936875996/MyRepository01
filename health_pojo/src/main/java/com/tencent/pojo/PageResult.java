package com.tencent.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * @author PR
 * @package com.tencent.pojo
 * @date 2021/1/6 17:20
 */
public class PageResult<T> implements Serializable {
    //总记录数
    private Long total;
    //查询结果集合
    private List<T> rows;

    public PageResult(Long total, List<T> rows) {
        this.total = total;
        this.rows = rows;
    }

    public PageResult() {
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    @Override
    public String toString() {
        return "PageResult{" +
                "total=" + total +
                ", rows=" + rows +
                '}';
    }
}
