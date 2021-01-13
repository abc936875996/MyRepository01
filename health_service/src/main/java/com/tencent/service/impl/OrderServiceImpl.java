package com.tencent.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.sun.org.apache.xerces.internal.impl.dv.xs.DateDV;
import com.tencent.constant.MessageConstant;
import com.tencent.dao.MemberDao;
import com.tencent.dao.OrderDao;
import com.tencent.dao.OrderSettingDao;
import com.tencent.exception.CannotOrderException;
import com.tencent.exception.OrderFullException;
import com.tencent.pojo.Member;
import com.tencent.pojo.Order;
import com.tencent.pojo.OrderSetting;
import com.tencent.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

/**
 * @author PR
 * @package com.tencent.service.impl
 * @date 2021/1/11 21:42
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    @Autowired
    private MemberDao memberDao;

    @Autowired
    private OrderSettingDao orderSettingDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Order orderSubmit(Map<String, String> orderInfo) throws ParseException {
        //0.转换日期
        String orderDateStr = orderInfo.get("orderDate");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date orderDate = sdf.parse(orderDateStr);

        //0.1获取参数
        String orderType = orderInfo.get("orderType");
        String name = orderInfo.get("name");
        String sex = orderInfo.get("sex");
        String telephone = orderInfo.get("telephone");
        String idCard = orderInfo.get("idCard");

        int setmealId = Integer.parseInt(orderInfo.get("setmealId"));


        //1.查询当日预约信息
        OrderSetting orderSetting = orderSettingDao.findByOrderDate(new OrderSetting(orderDate));
        //1.1 当日能否约
        if (orderSetting != null) {
            //1.2是否已约满
            if (orderSetting.getNumber() > orderSetting.getReservations()) {
                //2.判断是否注册
                Member member = memberDao.findMemberByPhoneNumber(telephone);
                if (member == null) {
                    //2.1未注册,进行注册并给初始密码
                    //2.2生成初始密码
                    String initPassword = telephone.substring(5);
                    member=new Member();
                    member.setName(name);
                    member.setSex(sex);
                    member.setPhoneNumber(telephone);
                    member.setRegTime(new Date());
                    member.setIdCard(idCard);
                    member.setPassword(initPassword);
                    memberDao.addMember(member);
                }
                //3.更新当日预约人数
                int count = orderSettingDao.addReservations(orderDate);
                //3.1判断是否更新成功
                if (count <= 0) {
                    //当日已约满，建议改日再约
                    throw new OrderFullException(MessageConstant.ORDER_FULL);
                }
                //4.添加预约信息(微信预约、电话预约)
                Order order = new Order();
                order.setSetmealId(setmealId);
                order.setOrderStatus("未到诊");
                order.setOrderType(orderType);
                order.setMemberId(member.getId());
                order.setOrderDate(orderDate);
                orderDao.addOrder(order);
                return order;
            } else {
                //1.3当日已约满，建议改日再约
                throw new OrderFullException(MessageConstant.ORDER_FULL);
            }
        } else {
            //1.2当日不可约
            throw new CannotOrderException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
    }
}
