package cn.mklaus.demo.event;

import cn.mklaus.demo.entity.Order;
import org.nutz.log.Logs;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Mklaus
 * @date 2018-03-27 上午11:43
 */
@Component
public class OrderPayEventListener implements ApplicationListener<OrderPayEvent> {

    @Async
    @Override
    public void onApplicationEvent(OrderPayEvent orderPayEvent) {
        Order order = orderPayEvent.getOrder();
        Logs.get().info("Handle order pay event: " + order.getOutTradeNo());

    }

}
