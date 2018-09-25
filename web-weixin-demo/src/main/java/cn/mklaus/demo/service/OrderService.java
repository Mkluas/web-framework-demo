package cn.mklaus.demo.service;

import cn.mklaus.demo.entity.Order;
import cn.mklaus.demo.entity.User;
import cn.mklaus.framework.base.BaseService;
import cn.mklaus.framework.bean.ServiceResult;

/**
 * @author klaus
 * @date 2018/9/25 上午11:06
 */
public interface OrderService extends BaseService<Order> {

    Order fetchByOutTradeNo(String outTradeNo);

    ServiceResult saveOrder(Order order);

    ServiceResult requestPayInfo(User user, Order order);

    ServiceResult handleOrderPayNotify(String data);

    ServiceResult refundOrder(String outTradeNo);

}
