package cn.mklaus.demo.service;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.mklaus.demo.entity.User;
import cn.mklaus.framework.base.BaseService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;

/**
 * @author klaus
 * @date 2018/9/4 下午6:27
 */
public interface UserService extends BaseService<User> {

    /**
     * 公众号
     */
    User getByOpenid(String openid);

    User getOrCreateByWxMpUser(WxMpUser mpUser);

    User getOrCreateByOpenid(String openid);

    /**
     * 小程序
     */
    User login(WxMaJscode2SessionResult sessionResult);

    User getByToken(String token);

}