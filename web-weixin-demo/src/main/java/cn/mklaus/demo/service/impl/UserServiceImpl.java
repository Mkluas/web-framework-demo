package cn.mklaus.demo.service.impl;

import cn.binarywang.wx.miniapp.bean.WxMaJscode2SessionResult;
import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.event.NewMpUserEvent;
import cn.mklaus.demo.service.UserService;
import cn.mklaus.framework.base.BaseServiceImpl;
import cn.mklaus.framework.config.EventPublisher;
import cn.mklaus.framework.util.Langs;
import cn.mklaus.framework.util.Times;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.nutz.dao.Cnd;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author klaus
 * @date 2018/9/4 下午6:27
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private EventPublisher eventPublisher;

    /**
     * 公众号
     */

    @Override
    public User getByOpenid(String openid) {
        return fetch(Cnd.where("openid", "=", openid));
    }

    @Override
    public User getOrCreateByWxMpUser(WxMpUser mpUser) {
        User user = getByOpenid(mpUser.getOpenId());
        if (user == null) {
            user = new User();
            setupMpUser(user, mpUser);
            insert(user);
            publishNewMpUserEvent(user);
        }
        return user;
    }

    @Override
    public User getOrCreateByOpenid(String openid) {
        User user = getByOpenid(openid);
        if (user == null){
            try {
                WxMpUser mpUser = wxMpService.getUserService().userInfo(openid);
                user = new User();
                setupMpUser(user, mpUser);
                insert(user);
                publishNewMpUserEvent(user);
            } catch (WxErrorException e) {
                throw new RuntimeException(e.toString(), e);
            }
        }
        return user;
    }

    private void setupMpUser(User user, WxMpUser mpUser) {
        user.setHeadimgurl(Optional.ofNullable(mpUser.getHeadImgUrl()).orElse(""));
        user.setNickname(Optional.ofNullable(mpUser.getNickname()).orElse(""));
        user.setOpenid(mpUser.getOpenId());
        user.setUnionid(Optional.ofNullable(mpUser.getUnionId()).orElse(""));
        user.setSex(Optional.ofNullable(mpUser.getSex()).orElse(2));
    }

    private void publishNewMpUserEvent(User user) {
        NewMpUserEvent event = new NewMpUserEvent(user);
        eventPublisher.publishEvent(event);
    }


    /**
     * 小程序
     */
    @Override
    public User login(WxMaJscode2SessionResult sessionResult) {
        User user = getByOpenid(sessionResult.getOpenid());
        if (user == null) {
            user = new User();
            user.setUnionid(sessionResult.getUnionid());
            user.setOpenid(sessionResult.getOpenid());
            user.setSessionKey(sessionResult.getSessionKey());
            user.setToken(Langs.uuid());
            insert(user);
        } else {
            user.setSessionKey(sessionResult.getSessionKey());
            user.setToken(Langs.uuid());
            user.setUpdateTime(Times.now());
            updateIgnoreNull(user);
        }
        return user;
    }

    @Override
    public User getByToken(String token) {
        return fetch(Cnd.where("token", "=", token));
    }
}