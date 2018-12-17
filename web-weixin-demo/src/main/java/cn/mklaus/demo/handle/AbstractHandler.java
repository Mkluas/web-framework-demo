package cn.mklaus.demo.handle;

import me.chanjar.weixin.common.bean.result.WxMediaUploadResult;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.kefu.WxMpKefuMessage;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;

/**
 * @author klaus
 * @date 2018/12/17 11:35 AM
 */
public abstract class AbstractHandler implements WxMpMessageHandler {

    @Autowired
    private WxMpService wxMpService;

    protected void sendTextMessage(String openid, String text) throws WxErrorException {
        WxMpKefuMessage welcomeMsg = WxMpKefuMessage.TEXT()
                .content(text)
                .toUser(openid)
                .build();
        wxMpService.getKefuService().sendKefuMessage(welcomeMsg);
    }

    protected void sendImageMessage(String openid, String mediaId) throws WxErrorException {
        WxMpKefuMessage welcomeMsg = WxMpKefuMessage.IMAGE()
                .mediaId(mediaId)
                .toUser(openid)
                .build();
        wxMpService.getKefuService().sendKefuMessage(welcomeMsg);
    }

    protected void sendImageMessage(String openid, File file) throws WxErrorException {
        WxMediaUploadResult res = wxMpService.getMaterialService().mediaUpload("image", file);
        sendImageMessage(openid, res.getMediaId());
    }

}
