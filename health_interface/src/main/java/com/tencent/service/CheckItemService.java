package com.tencent.service;

import com.tencent.exception.CheckItemUsedException;
import com.tencent.pojo.CheckItem;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.service
 * @date 2021/1/5 17:50
 */
public interface CheckItemService  {
    void addCheckItem(CheckItem checkItem);

    PageResult<CheckItem> findPage(QueryPageBean queryPageBean);

    List<CheckItem> findList();

    void deleteById(Integer id) throws CheckItemUsedException;
}
