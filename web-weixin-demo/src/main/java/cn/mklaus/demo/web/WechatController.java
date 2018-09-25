package cn.mklaus.demo.web;

import cn.mklaus.demo.service.OrderService;
import cn.mklaus.demo.service.WechatService;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.wechat.WechatConsts;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author klaus
 * @date 2018/9/4 下午6:29
 */
@RestController
@RequestMapping("wechat")
public class WechatController {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatService wechatService;
    @Autowired
    private OrderService orderService;

    @GetMapping
    public String get(String signature, String timestamp, String nonce, String echostr) {
        return wxMpService.checkSignature(timestamp,nonce,signature) ? echostr : "fail";
    }

    @PostMapping(produces = "application/xml;charset=utf8")
    public String post(HttpServletRequest req) throws Exception {
        WxMpXmlMessage inMessage = WxMpXmlMessage.fromXml(req.getInputStream());
        WxMpXmlOutMessage outMessage = this.wechatService.route(inMessage);
        return outMessage != null ? outMessage.toXml() : "";
    }

    @PostMapping(value = "pay/notify", produces = "application/xml;charset=utf8")
    public String notify(HttpServletRequest req) throws IOException {
        String data = IOUtils.toString(req.getInputStream(), Charset.forName("utf-8"));
        ServiceResult result = orderService.handleOrderPayNotify(data);
        return result.isOk()
                ? WechatConsts.NOTIFY_SUCCESS_RESPONSE
                : WechatConsts.NOTIFY_FAIL_RESPONSE;
    }
}
