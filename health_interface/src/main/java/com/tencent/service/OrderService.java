package com.tencent.service;

import com.tencent.pojo.Order;

import java.text.ParseException;
import java.util.Map;

/**
 * @author PR
 * @package com.tencent.service
 * @date 2021/1/11 21:21
 */
public interface OrderService {
    Order orderSubmit(Map<String, String> order) throws ParseException;
}
