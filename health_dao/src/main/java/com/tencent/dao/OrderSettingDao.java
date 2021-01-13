package com.tencent.dao;

import com.tencent.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author PR
 * @package com.tencent.dao
 * @date 2021/1/9 18:09
 */
public interface OrderSettingDao {
    /**
     * 查询记录
     * @param orderSetting
     * @return
     */
    OrderSetting findByOrderDate(OrderSetting orderSetting);

    /**
     * 更新记录
     * @param orderSetting
     * @return
     */
    void updateSetting(OrderSetting orderSetting);

    /**
     * 插入数据
     * @param orderSetting
     */
    void insertSetting(OrderSetting orderSetting);

    List<Map<String, Integer>> findByMonth(String date);

    /**
     * 添加预约人数
     * @param orderDate
     * @return
     */
    Integer addReservations(Date orderDate);
}
