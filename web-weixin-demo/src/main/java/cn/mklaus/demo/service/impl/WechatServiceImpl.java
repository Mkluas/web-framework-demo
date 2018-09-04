package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.service.WechatService;
import cn.mklaus.framework.wechat.WechatProperties;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageRouter;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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

        this.router = newRouter;
    }
}