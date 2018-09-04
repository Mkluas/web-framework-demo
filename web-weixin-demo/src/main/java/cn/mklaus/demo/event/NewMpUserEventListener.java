package cn.mklaus.demo.event;

import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.UserService;
import cn.mklaus.framework.support.Qinius;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author Mklaus
 * @date 2018-08-17 上午10:49
 */
@Component
public class NewMpUserEventListener implements ApplicationListener<NewMpUserEvent> {

    @Autowired
    private UserService userService;

    @Async
    @Override
    public void onApplicationEvent(NewMpUserEvent event) {
        User user = event.getUser();
        if (isWechatImage(user.getHeadimgurl())) {
            String imageUrl = Qinius.convertToQiniu(user.getHeadimgurl());
            Cnd condition = Cnd.where("id", "=", user.getId());
            userService.update(Chain.make("headimgurl", imageUrl), condition);
        }
    }

    private boolean isWechatImage(String headimgurl) {
        return headimgurl.contains("qlogo.cn");
    }

}
