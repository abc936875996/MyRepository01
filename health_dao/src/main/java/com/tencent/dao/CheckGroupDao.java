package com.tencent.dao;

import com.github.pagehelper.Page;
import com.tencent.pojo.CheckGroup;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.dao
 * @date 2021/1/7 9:51
 */
public interface CheckGroupDao {
    void addCheckGroup(CheckGroup checkGroup);

    void addCheckItemAndGroup(@Param("checkGroupId") Integer checkGroupId, @Param("checkitemId") Integer checkitemId);

    Page<CheckGroup> findPage(String queryString);

    List<CheckGroup> findAll();
}
