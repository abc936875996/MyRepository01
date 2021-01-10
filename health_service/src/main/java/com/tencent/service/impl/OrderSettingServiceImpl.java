package com.tencent.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.tencent.dao.OrderSettingDao;
import com.tencent.exception.OrderSettingException;
import com.tencent.pojo.OrderSetting;
import com.tencent.service.OrderSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * @author PR
 * @package com.tencent.service.impl
 * @date 2021/1/9 17:48
 */
@Service(interfaceClass = OrderSettingService.class)
public class OrderSettingServiceImpl implements OrderSettingService {
    @Autowired
    private OrderSettingDao orderSettingDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void orderSetting(List<OrderSetting> settingList) {
        //遍历
        for (OrderSetting orderSetting : settingList) {
            //查询是否存在该日期并返回该设置数据
            OrderSetting osInDb = orderSettingDao.findByOrderDate(orderSetting);
            //判断是否存在
            if (osInDb != null) {
                //存在,判断要设置的最大预约数与已预约数
                if (osInDb.getNumber() == orderSetting.getNumber()) {
                } else if (osInDb.getReservations() < orderSetting.getNumber()){
                    //已预约数小于要设置的最大预约数，更新数据
                    orderSettingDao.updateSetting(orderSetting);
                }else {
                    //大于，报错
                    SimpleDateFormat sdf=new SimpleDateFormat("yyyy/MM/dd");
                    String date = sdf.format(orderSetting.getOrderDate());
                    throw new OrderSettingException(date+":最大预约人数不得小于已经预约的人数,请修改后重新提交。");
                }
            }else {
                //不存在，插入数据
                orderSettingDao.insertSetting(orderSetting);
            }
        }

    }

    @Override
    public List<Map<String, Integer>> findByMonth(String dateStr) {
        String date = dateStr + "%";
        return orderSettingDao.findByMonth(date);
    }
}
