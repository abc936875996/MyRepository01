package com.tencent.service;

import com.tencent.exception.OrderSettingException;
import com.tencent.pojo.OrderSetting;

import java.util.List;
import java.util.Map;

/**
 * @author PR
 * @package com.tencent.service
 * @date 2021/1/9 17:40
 */
public interface OrderSettingService {
    void orderSetting(List<OrderSetting> settingList) throws OrderSettingException;

    List<Map<String, Integer>> findByMonth(String dateStr);
}
