package com.tencent.dao;

import com.tencent.pojo.Order;

/**
 * @author PR
 * @package com.tencent.dao
 * @date 2021/1/11 21:46
 */
public interface OrderDao {
    Integer addOrder(Order order);
}
