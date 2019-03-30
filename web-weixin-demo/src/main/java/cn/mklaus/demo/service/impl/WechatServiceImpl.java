package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.handle.ScanQrCodeHandler;
import cn.mklaus.demo.handle.SubscribeHandler;
import cn.mklaus.demo.handle.UnSubscribeHandler;
import cn.mklaus.demo.service.WechatService;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.wechat.WechatProperties;
import me.chanjar.weixin.common.api.WxConsts;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.bean.menu.WxMenu;
import me.chanjar.weixin.common.bean.menu.WxMenuButton;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

/**
 * @author klaus
 * @date 2018/9/4 下午6:31
 */
@Service
public class WechatServiceImpl implements WechatService, InitializingBean {

    private static final Logger log = LoggerFactory.getLogger(WechatServiceImpl.class);

    private WxMpMessageRouter router;

    @Resource
    private WxMpService wxMpService;
    @Resource
    private WechatProperties.Mp mp;
    @Autowired
    private SubscribeHandler subscribeHandler;
    @Autowired
    private UnSubscribeHandler unSubscribeHandler;
    @Autowired
    private ScanQrCodeHandler scanQrCodeHandler;

    @Override
    public WxMpXmlOutMessage route(WxMpXmlMessage inMessage) {
        return router.route(inMessage);
    }

    @Override
    public WxJsapiSignature createSignature(HttpServletRequest req) {
        try {
            String path = req.getRequestURI() + (req.getQueryString() != null ? "?" + req.getQueryString() : "");
            WxJsapiSignature signature = wxMpService.createJsapiSignature(mp.getDomain() + path);
            return signature;
        } catch (WxErrorException e) {
            log.error("Create signature error: {}", e);
            return null;
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        final WxMpMessageRouter newRouter = new WxMpMessageRouter(this.wxMpService);

        // Logger
        newRouter.rule()
                .async(false)
                .handler((wxMpXmlMessage, map, wxMpService, wxSessionManager) -> {
                    log.info("Receive from wechat: {}", wxMpXmlMessage.toString());
                    return null;
                })
                .next();


        // subscribe event
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.SUBSCRIBE)
                .handler(this.subscribeHandler)
                .next();

        // unSubscribe event
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .event(WxConsts.EventType.UNSUBSCRIBE)
                .handler(this.unSubscribeHandler)
                .end();

        // scan QRCode event
        newRouter.rule().async(false).msgType(WxConsts.XmlMsgType.EVENT)
                .interceptor((message, map, wxMpService, wxSessionManager) -> Strings.isNotBlank(message.getEventKey()))
                .handler(this.scanQrCodeHandler)
                .end();

        this.router = newRouter;
    }

    @Override
    public ServiceResult createMenu() {
        WxMenuButton viewBtn = new WxMenuButton();
        viewBtn.setType(WxConsts.MenuButtonType.VIEW);
        viewBtn.setUrl("http://baidu.com");
        viewBtn.setName("百度");

        WxMenuButton clickBtn = new WxMenuButton();
        clickBtn.setType(WxConsts.MenuButtonType.CLICK);
        clickBtn.setKey("click");
        clickBtn.setName("按钮");

        WxMenu wxMenu = new WxMenu();
        wxMenu.setButtons(Arrays.asList(viewBtn, clickBtn));

        try {
            wxMpService.getMenuService().menuCreate(wxMenu);
            return ServiceResult.ok();
        } catch (WxErrorException e) {
            e.printStackTrace();
            return ServiceResult.error(e.toString());
        }
    }


}