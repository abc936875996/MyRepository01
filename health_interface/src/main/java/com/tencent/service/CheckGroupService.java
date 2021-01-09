package com.tencent.service;

import com.tencent.pojo.CheckGroup;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;

/**
 * @author PR
 * @package com.tencent.service
 * @date 2021/1/7 9:46
 */
public interface CheckGroupService {
    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult<CheckGroup> findPage(QueryPageBean queryPageBean);

    PageResult<CheckGroup> findAll();
}
