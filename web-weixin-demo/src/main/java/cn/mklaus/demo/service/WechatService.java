package cn.mklaus.demo.service;

import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.bean.message.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.message.WxMpXmlOutMessage;

import javax.servlet.http.HttpServletRequest;

/**
 * @author klaus
 * @date 2018/9/4 下午6:30
 */
public interface WechatService {

    WxMpXmlOutMessage route(WxMpXmlMessage inMessage);

    WxJsapiSignature createSignature(HttpServletRequest req);

}
