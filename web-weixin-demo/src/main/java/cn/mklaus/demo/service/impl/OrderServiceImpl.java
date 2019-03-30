package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.conf.OrderNoEnum;
import cn.mklaus.demo.entity.Order;
import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.event.OrderPayEvent;
import cn.mklaus.demo.service.OrderService;
import cn.mklaus.framework.base.BaseServiceImpl;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.config.EventPublisher;
import cn.mklaus.framework.util.Times;
import com.github.binarywang.wxpay.bean.notify.WxPayOrderNotifyResult;
import com.github.binarywang.wxpay.bean.order.WxPayMpOrderResult;
import com.github.binarywang.wxpay.bean.request.WxPayRefundRequest;
import com.github.binarywang.wxpay.bean.request.WxPayUnifiedOrderRequest;
import com.github.binarywang.wxpay.bean.result.WxPayRefundResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.nutz.dao.Cnd;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author klaus
 * @date 2018/9/25 上午11:06
 */
@Service
public class OrderServiceImpl extends BaseServiceImpl<Order> implements OrderService {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Qualifier("wxMpPayService")
    @Autowired
    private WxPayService wxPayService;
    @Autowired
    private EventPublisher eventPublisher;

    @Override
    public Order fetchByOutTradeNo(String outTradeNo) {
        return fetch(Cnd.where("outTradeNo", "=", outTradeNo));
    }

    @Override
    public ServiceResult saveOrder(Order order) {
        // TODO: 2018/9/25 init order.

        order.setStatus(Order.Status.NEW);
        order.setOutTradeNo(OrderNoEnum.OUT_TRADE_NO.next());
        insert(order);
        return ServiceResult.ok();
    }

    @Override
    public ServiceResult requestPayInfo(User user, Order order) {
        // TODO: 2018/9/25 check order。

        try {
            WxPayMpOrderResult payInfo = requestPayInfo(order);
            return ServiceResult.ok().put("payInfo", payInfo);
        } catch (WxPayException e) {
            e.printStackTrace();
            return ServiceResult.error(e.toString());
        }
    }

    @Override
    public ServiceResult handleOrderPayNotify(String data) {
        try {
            WxPayOrderNotifyResult notify = wxPayService.parseOrderNotifyResult(data);
            Order order = fetchByOutTradeNo(notify.getOutTradeNo());

            if (Strings.isBlank(order.getTransactionId())) {
                order.setTransactionId(notify.getTransactionId());
                order.setPayTime(Times.now());
                order.setStatus(Order.Status.PAID);
                int update = updateWithVersion(order);
                if (update > 0) {
                    eventPublisher.publishEvent(new OrderPayEvent(order));
                }
            }

            return ServiceResult.ok();

        } catch (WxPayException e) {
            log.error("Handle order notify error: {}", e.getMessage());
            return ServiceResult.error("handle fail");
        }
    }

    @Override
    public ServiceResult refundOrder(String outTradeNo) {
        Order order = fetchByOutTradeNo(outTradeNo);
        Assert.state(Order.Status.CANCEL.equals(order.getStatus()), "不合法操作");
        String outRefundNo = OrderNoEnum.OUT_REFUND_NO.next();
        WxPayRefundRequest request = WxPayRefundRequest.newBuilder()
                .opUserId(wxPayService.getConfig().getMchId())
                .outTradeNo(outTradeNo)
                .totalFee(order.getTotalFee())
                .refundFee(order.getTotalFee())
                .outRefundNo(outRefundNo)
                .build();

        try {
            WxPayRefundResult refund = wxPayService.refund(request);
            order.setOutRefundNo(outRefundNo);
            order.setRefundId(refund.getRefundId());
            order.setStatus(Order.Status.REFUNDED);
            updateIgnoreNull(order);
        } catch (WxPayException e) {
            return ServiceResult.error(e.getErrCodeDes());
        }
        return ServiceResult.ok();
    }

    private WxPayMpOrderResult requestPayInfo(Order order) throws WxPayException {
        WxPayUnifiedOrderRequest request = WxPayUnifiedOrderRequest.newBuilder()
                .tradeType("JSAPI")
                .body("微信支付订单")
                .openid(order.getOpenid())
                .spbillCreateIp("127.0.0.1")
                .outTradeNo(order.getOutTradeNo())
                .notifyUrl(wxPayService.getConfig().getNotifyUrl())
                .totalFee(order.getTotalFee())
                .build();

        return wxPayService.createOrder(request);
    }


}
