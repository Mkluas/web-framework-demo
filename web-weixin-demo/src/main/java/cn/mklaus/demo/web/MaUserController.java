package cn.mklaus.demo.web;

import cn.binarywang.wx.miniapp.api.WxMaService;
import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.UserService;
import cn.mklaus.framework.bean.Response;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import me.chanjar.weixin.common.error.WxErrorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mklaus
 * @date 2018-07-12 上午11:38
 */
@Api(description = "小程序用户接口")
@RestController
@RequestMapping("ma/user")
public class MaUserController {

    @Autowired
    private WxMaService wxMaService;
    @Autowired
    private UserService userService;


    @ApiOperation(value = "小程序用户登录获取token", notes = "以后的请求都需要带上token作为登录凭证")
    @PostMapping("login")
    public JSONObject login(@RequestParam String code) {
        try {
            WxMaJscode2SessionResult sessionInfo = wxMaService.getUserService().getSessionInfo(code);
            User user = userService.login(sessionInfo);
            return Response.ok().put("token", user.getToken()).build();
        } catch (WxErrorException e) {
            return Response.error(e.getError().toString()).build();
        }
    }

}
