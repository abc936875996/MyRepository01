package com.tencent.dao;

import com.github.pagehelper.Page;
import com.tencent.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.dao
 * @date 2021/1/8 19:34
 */
public interface SetmealDao {
    void addSetmeal(Setmeal setmeal);

    void addSetmealAndCheckGroup(@Param("setmealId") Integer setmealId,@Param("checkgroupId") Integer checkgroupId);

    Page<Setmeal> findPage(String queryString);

    List<String> findImgList();

}
