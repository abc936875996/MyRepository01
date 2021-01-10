package com.tencent.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.constant.MessageConstant;
import com.tencent.pojo.OrderSetting;
import com.tencent.pojo.Result;
import com.tencent.service.OrderSettingService;
import com.tencent.utils.POIUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author PR
 * @package com.tencent.controller
 * @date 2021/1/9 17:30
 */
@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    @PostMapping("/upload")
    public Result excelUpload(@RequestBody MultipartFile excelFile) throws IOException, ParseException {
        //解析exmal
        List<String[]> settingList = POIUtils.readExcel(excelFile);
        //日期格式解析
        List<OrderSetting> orderSettingList = new ArrayList<>();
        for (String[] setting : settingList) {
            //获得日期
            String orderDateString = setting[0];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
            Date orderDate = sdf.parse(orderDateString);
            //获得最大预约数
            int number = Integer.parseInt(setting[1]);
            //封装
            orderSettingList.add(new OrderSetting(orderDate, number));
        }

        orderSettingService.orderSetting(orderSettingList);

        Result result = new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        return result;
    }

    @PostMapping("getByMonth")
    public Result getByMonth(String dateStr) {
        List<Map<String, Integer>> list = orderSettingService.findByMonth(dateStr);
        return new Result(true, MessageConstant.QUERY_ORDER_SUCCESS,list);
    }
}
