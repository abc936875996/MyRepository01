package com.tencent.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.constant.MessageConstant;
import com.tencent.pojo.CheckGroup;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;
import com.tencent.pojo.Result;
import com.tencent.service.CheckGroupService;
import org.springframework.web.bind.annotation.*;

/**
 * @author PR
 * @package com.tencent.controller
 * @date 2021/1/6 21:59
 */
@RestController
@RequestMapping("/checkGroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;

    @PostMapping("/add")
    public Result addCheckGroup(@RequestBody CheckGroup checkGroup, Integer[] checkitemIds) {
        Result result;
        try {
            checkGroupService.add(checkGroup, checkitemIds);
            result = new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
        return result;
    }

    @PostMapping("/findPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        Result result;
        try {
            PageResult<CheckGroup> pageResult = checkGroupService.findPage(queryPageBean);
            result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            result = new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
        return result;
    }

    @PostMapping("/findAll")
    public Result findAll() {
        PageResult<CheckGroup> pageResult = checkGroupService.findAll();
        Result result = new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        return result;
    }
}
