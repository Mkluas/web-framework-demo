package cn.mklaus.demo.event;

import cn.mklaus.demo.entity.User;
import org.springframework.context.ApplicationEvent;

/**
 * @author Mklaus
 * @date 2018-08-17 上午10:47
 */
public class NewMpUserEvent extends ApplicationEvent {

    public NewMpUserEvent(Object source) {
        super(source);
    }

    public User getUser() {
        return (User)source;
    }

}
