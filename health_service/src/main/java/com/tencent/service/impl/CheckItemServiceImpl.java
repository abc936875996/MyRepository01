package com.tencent.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.tencent.dao.CheckItemDao;
import com.tencent.exception.CheckItemUsedException;
import com.tencent.pojo.CheckItem;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;
import com.tencent.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.service.impl
 * @date 2021/1/5 19:53
 */
//指定服务的接口类，若不指定则看implements
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {
    @Autowired
    private CheckItemDao checkItemDao;

    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        //限制最大查询条数
        if (queryPageBean.getPageSize() >= 50) {
            queryPageBean.setPageSize(50);
        }
        //调用分页工具
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        //模糊查询添加%
        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        //调用dao查询
        Page<CheckItem> page = checkItemDao.findByPage(queryPageBean.getQueryString());

        PageResult<CheckItem> pageResult = new PageResult<>(page.getTotal(), page.getResult());

        return pageResult;
    }

    @Override
    public List<CheckItem> findList() {
        return checkItemDao.findList();
    }

    @Override
    public void deleteById(Integer id) {
        //判断该检查项是否被检查组使用
        Long usageCount = checkItemDao.findUsageCount(id);
        if (usageCount>0) {
            //不能删除
            throw new CheckItemUsedException("该检查项已被检查组使用,请删除关联的检查组后重试！");
        }
        checkItemDao.deleteById(id);
    }

    @Override
    public void addCheckItem(CheckItem checkItem) {
        checkItemDao.addCheckItem(checkItem);
    }
}
