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
 * @date 2019/1/14 4:40 PM
 */
@Component
public class UnSubscribeHandler extends AbstractHandler {

    @Autowired
    private UserService userService;

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        User user = userService.getOrCreateByOpenid(wxMessage.getFromUser());
        user.setSubscribe(false);
        userService.update(user);
        return null;
    }
}
