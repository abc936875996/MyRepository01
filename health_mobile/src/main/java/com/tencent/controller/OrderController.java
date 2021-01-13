package com.tencent.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.tencent.constant.MessageConstant;
import com.tencent.constant.RedisMessageConstant;
import com.tencent.pojo.Order;
import com.tencent.pojo.Result;
import com.tencent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.ParseException;
import java.util.Map;

/**
 * @author PR
 * @package com.tencent.controller
 * @date 2021/1/11 21:01
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    @Reference
    private OrderService orderService;

    @Autowired
    private JedisPool jedisPool;

    @PostMapping("/submit")
    public Result submitOrder(@RequestBody Map<String, String> orderInfo) throws ParseException {
        String validateCode = orderInfo.get("validateCode");
        String telephone = orderInfo.get("telephone");
        //1.判断短信验证码
        //1.1从Redis中取出验证码
        Jedis jedis = jedisPool.getResource();
        String validateCodeInDb = jedis.get(RedisMessageConstant.SENDTYPE_ORDER + "_" + telephone);
        //1.2验证码是否存在
        if (validateCodeInDb != null) {
            //1.3存在，判断验证码是否正确
            if (validateCodeInDb.equals(validateCode)) {
                //1.4正确，提交订单
                Order order = orderService.orderSubmit(orderInfo);
                //1.5提交成功
                return new Result(true, MessageConstant.ORDER_SUCCESS, order);
            } else {
                //1.4验证码错误
                return new Result(false, MessageConstant.VALIDATECODE_ERROR);
            }
        } else {
            //1.3不存在，提示重新获取
            return new Result(false, MessageConstant.VALIDATECODE_EXPIRED);
        }
    }
}
