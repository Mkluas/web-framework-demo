package cn.mklaus.demo.handle;

import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author klaus
 * @date 2018/8/20 下午5:37
 */
@Component
public class ScanQrCodeHandler extends AbstractHandler {

    private static String QRCODE_EVENT_KEY_PREFIX = "qrscene_";

    @Override
    public WxMpXmlOutMessage handle(WxMpXmlMessage message, Map<String, Object> map, WxMpService wxMpService, WxSessionManager wxSessionManager) throws WxErrorException {
        String eventKey = message.getEventKey();
        if (eventKey.startsWith(QRCODE_EVENT_KEY_PREFIX)) {
            eventKey = eventKey.substring(QRCODE_EVENT_KEY_PREFIX.length());
        }
        if (Strings.isNotBlank(eventKey)) {
            // to-do
        }
        return null;
    }

}
