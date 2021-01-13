package com.tencent.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.constant.MessageConstant;
import com.tencent.pojo.Result;
import com.tencent.pojo.Setmeal;
import com.tencent.service.SetmealService;
import com.tencent.utils.QiNiuUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.controller
 * @date 2021/1/10 17:01
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    @Reference
    private SetmealService setmealService;

    @GetMapping("/getSetmeal")
    public Result getSetmealList() {
        List<Setmeal> setmealList = setmealService.findSetmealList();
        setmealList.forEach(setmeal -> setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg()));

        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmealList);
    }

    /**
     * checkgroup in setmeal.checkGroups;checkitem in checkgroup.checkItems
     */
    @GetMapping("/findDetailById")
    public Result findDetailById(Integer id) {
        Setmeal setmeal = setmealService.findSetmealDetailById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());

        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmeal);
    }

    @GetMapping("/findById")
    public Result findById(int id) {
        Setmeal setmeal = setmealService.findById(id);
        setmeal.setImg(QiNiuUtils.DOMAIN + setmeal.getImg());
        return new Result(true, MessageConstant.GET_SETMEAL_LIST_SUCCESS, setmeal);
    }
}
