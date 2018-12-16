package cn.mklaus.demo.web;

import cn.mklaus.demo.conf.UserHolder;
import cn.mklaus.demo.entity.Order;
import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.OrderService;
import cn.mklaus.demo.service.WechatService;
import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.wechat.WechatConsts;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.Charset;

/**
 * @author klaus
 * @date 2018/12/16 10:19 PM
 */
@Controller
@RequestMapping("order")
public class OrderController {

    @Autowired
    private WechatService wechatService;
    @Autowired
    private OrderService orderService;

    @GetMapping("info")
    public String info(@RequestParam String outTradeNo,
                       HttpServletRequest req, Model model) {
        Order order = orderService.fetchByOutTradeNo(outTradeNo);
        model.addAttribute("order", JSON.toJSONString(order));
        model.addAttribute("wx", wechatService.createSignature(req));
        return "wxpay";
    }

    @PostMapping("pay")
    @ResponseBody
    public JSONObject pay(@RequestParam String outTradeNo) {
        User user = UserHolder.get();
        Order order = orderService.fetchByOutTradeNo(outTradeNo);
        ServiceResult result = orderService.requestPayInfo(user, order);
        return Response.withResult(result).build();
    }


    @PostMapping(value = "pay/notify", produces = "application/xml;charset=utf8")
    @ResponseBody
    public String notify(HttpServletRequest req) throws IOException {
        String data = IOUtils.toString(req.getInputStream(), Charset.forName("utf-8"));
        ServiceResult result = orderService.handleOrderPayNotify(data);
        return result.isOk()
                ? WechatConsts.NOTIFY_SUCCESS_RESPONSE
                : WechatConsts.NOTIFY_FAIL_RESPONSE;
    }

}
