package com.tencent.pojo;

import java.io.Serializable;

/**
 * @author PR
 * @package com.tencent.pojo
 * @date 2021/1/6 18:15
 */
public class QueryPageBean implements Serializable {
    private Integer currentPage;
    private Integer pageSize;
    private String queryString;

    public QueryPageBean() {
    }

    public QueryPageBean(Integer currentPage, Integer pageSize, String queryString) {
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.queryString = queryString;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getQueryString() {
        return queryString;
    }

    public void setQueryString(String queryString) {
        this.queryString = queryString;
    }

    @Override
    public String toString() {
        return "QueryPageBean{" +
                "currentPage=" + currentPage +
                ", pageSize=" + pageSize +
                ", queryString='" + queryString + '\'' +
                '}';
    }
}
