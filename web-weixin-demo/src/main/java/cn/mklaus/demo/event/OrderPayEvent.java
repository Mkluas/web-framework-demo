package cn.mklaus.demo.event;

import cn.mklaus.demo.entity.Order;
import org.springframework.context.ApplicationEvent;

/**
 * @author Mklaus
 * @date 2018-03-27 上午11:41
 */
public class OrderPayEvent extends ApplicationEvent {

    private Order order;

    public OrderPayEvent(Object source) {
        this(source, (Order) source);
    }

    public OrderPayEvent(Object source, Order order) {
        super(source);
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }

}
