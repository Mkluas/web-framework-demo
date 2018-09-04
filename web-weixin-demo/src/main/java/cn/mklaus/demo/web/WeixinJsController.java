package cn.mklaus.demo.web;

import cn.mklaus.demo.service.WechatService;
import me.chanjar.weixin.common.bean.WxJsapiSignature;
import me.chanjar.weixin.mp.api.WxMpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author Mklaus
 * @date 2018-08-17 上午9:39
 */
@Controller
@RequestMapping("wechat")
public class WeixinJsController {

    @Resource
    private WxMpService wxMpService;
    @Autowired
    private WechatService wechatService;

    @GetMapping("share")
    public String share(Model model, HttpServletRequest req) {
        WxJsapiSignature signature = wechatService.createSignature(req);
        model.addAttribute("wx", signature);
        return "demo/share";
    }


}
