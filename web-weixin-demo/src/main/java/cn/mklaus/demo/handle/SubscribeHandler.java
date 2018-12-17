package cn.mklaus.demo.handle;

import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.UserService;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author klaus
 * Created by klaus on 6/27/17.
 */
@Component
public class SubscribeHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        User user = userService.getOrCreateByOpenid(wxMessage.getFromUser());
        user.setSubscribe(true);
        userService.update(user);
        sendTextMessage(user.getOpenid(), "欢迎关注星零花");
        return null;
    }

}
