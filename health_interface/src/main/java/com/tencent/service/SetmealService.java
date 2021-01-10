package com.tencent.service;

import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;
import com.tencent.pojo.Setmeal;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.service
 * @date 2021/1/8 19:28
 */
public interface SetmealService {
    void add(Setmeal setmeal, Integer[] checkgroupIds);

    PageResult<Setmeal> findPage(QueryPageBean queryPageBean);

    List<String> findImgList();

    List<Setmeal> findSetmealList();

    Setmeal findSetmealDetailById(Integer id);
}
