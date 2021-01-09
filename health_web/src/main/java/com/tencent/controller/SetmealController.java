package com.tencent.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.constant.MessageConstant;
import com.tencent.pojo.PageResult;
import com.tencent.pojo.QueryPageBean;
import com.tencent.pojo.Result;
import com.tencent.pojo.Setmeal;
import com.tencent.service.SetmealService;
import com.tencent.utils.QiNiuUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author PR
 * @package com.tencent.controller
 * @date 2021/1/8 17:02
 */
@RestController
@RequestMapping("/setmeal")
public class SetmealController {
    private static final Logger log = LoggerFactory.getLogger(SetmealController.class);

    @Reference
    private SetmealService setmealService;

    @PostMapping("/upload")
    public Result imageUpload(MultipartFile imgFile) {
        Result result = null;
        //获取源文件名
        String originalFilename = imgFile.getOriginalFilename();
        //获取文件后缀名
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //生成唯一文件名,拼接后缀名
        String uniqueFileName = UUID.randomUUID() + suffix;
        try {
            //调用七牛工具上传文件
            QiNiuUtils.uploadViaByte(imgFile.getBytes(), uniqueFileName);
            //响应数据给页面
            Map<String, String> map = new HashMap<>(2);
            map.put("domain", QiNiuUtils.DOMAIN);
            map.put("imgName", uniqueFileName);
            result = new Result(true, MessageConstant.PIC_UPLOAD_SUCCESS, map);
        } catch (IOException e) {
            log.error("上传文件失败！");
            result = new Result(false, MessageConstant.PIC_UPLOAD_FAIL);
        }
        return result;
    }

    @PostMapping("/add")
    public Result add(@RequestBody Setmeal setmeal, Integer[] checkgroupIds) {
        setmealService.add(setmeal, checkgroupIds);
        Result result = new Result(true, MessageConstant.ADD_SETMEAL_SUCCESS);
        return result;
    }

    @PostMapping("/findByPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult<Setmeal> pageResult = setmealService.findPage(queryPageBean);
        Result result = new Result(true, MessageConstant.QUERY_SETMEAL_SUCCESS, pageResult);
        return result;
    }
}