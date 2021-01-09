package com.tencent.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.StringUtil;
import com.tencent.dao.SetmealDao;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;
import com.tencent.pojo.Setmeal;
import com.tencent.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.service.impl
 * @date 2021/1/8 19:33
 */
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //添加套餐
        setmealDao.addSetmeal(setmeal);
        //获得套餐id
        Integer setmealId = setmeal.getId();
        //添加套餐
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealAndCheckGroup(setmealId, checkgroupId);
            }
        }
    }

    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        if (StringUtil.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        Page<Setmeal> page = setmealDao.findPage(queryPageBean.getQueryString());
        PageResult<Setmeal> pageResult = new PageResult<>(page.getTotal(), page.getResult());
        return pageResult;
    }

    @Override
    public List<String> findImgList() {
        List<String> imgList =setmealDao.findImgList();
        return imgList;
    }
}
