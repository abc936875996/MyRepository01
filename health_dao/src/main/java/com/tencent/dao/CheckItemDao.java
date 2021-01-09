package com.tencent.dao;

import com.github.pagehelper.Page;
import com.tencent.pojo.CheckItem;
import com.tencent.pojo.PageResult;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.dao
 * @date 2021/1/5 20:24
 */
public interface CheckItemDao {
    Page<CheckItem> findByPage(String queryString);

    void addCheckItem(CheckItem checkItem);

    List<CheckItem> findList();

    Long findUsageCount(int id);

    void deleteById(int id);
}
