package com.tencent.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.tencent.dao.CheckGroupDao;
import com.tencent.pojo.CheckGroup;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;
import com.tencent.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.service.impl
 * @date 2021/1/7 9:50
 */
// 使用alibaba的包，发布服务 interfaceClass指定服务的接口类
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        //添加检查组，返回id
        checkGroupDao.addCheckGroup(checkGroup);
        //获得检查组id
        Integer checkGroupId = checkGroup.getId();
        //遍历选中检查项id添加检查组与检查项关系
        if (checkitemIds != null) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.addCheckItemAndGroup(checkGroupId, checkitemId);
            }
        } else {
            throw new RuntimeException("");
        }
    }

    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        //限制查询条数
        if (queryPageBean.getPageSize() > 50) {
            queryPageBean.setPageSize(50);
        }
        //设置分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //设置模糊查询条件
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //以下查询语句将被分页
        Page<CheckGroup> page = checkGroupDao.findPage(queryPageBean.getQueryString());
        PageResult<CheckGroup> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public PageResult<CheckGroup> findAll() {
        List<CheckGroup> list = checkGroupDao.findAll();
        PageResult<CheckGroup> pageResult = new PageResult<>(null, list);
        return pageResult;
    }
}
