package com.tencent.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.constant.MessageConstant;
import com.tencent.pojo.CheckItem;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;
import com.tencent.pojo.Result;
import com.tencent.service.CheckItemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author PR
 * @package com.tencent.controller
 * @date 2021/1/5 19:54
 */
@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    @RequestMapping("/findPage")
    public Result findByPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<CheckItem> pageResult = checkItemService.findPage(queryPageBean);
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
        return result;
    }

    @RequestMapping("/findList")
    public Result findList() {
        List<CheckItem> checkItemList = checkItemService.findList();
        Result result = new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemList);
        return result;
    }

    @PostMapping("/addCheckItem")
    public Result addCheckItem(@RequestBody CheckItem checkItem) {
        checkItemService.addCheckItem(checkItem);
        Result result = new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        return result;
    }

    @GetMapping("/deleteById")
    public Result deleteCheckItem(Integer id) {
        checkItemService.deleteById(id);
        Result result = new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        return result;
    }
}
