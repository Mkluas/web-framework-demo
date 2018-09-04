package cn.mklaus.demo.conf;

import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.UserService;
import cn.mklaus.framework.util.Langs;
import cn.mklaus.framework.wechat.AbstractWechatInterceptor;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.result.WxMpUser;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Klaus
 * @date 2018/8/12
 */
public class WxMpUserInterceptor extends AbstractWechatInterceptor<User> {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private UserService userService;

    @Override
    protected WxMpService getWxMpService() {
        return wxMpService;
    }

    @Override
    protected boolean passMe(HttpServletRequest req) {
        if (Langs.IS_MAC_OS) {
            User user = userService.fetch(1);
            saveUserContext(user, req);
            return true;
        }
        return false;
    }

    @Override
    protected User getByTokenBox(TokenBox tokenBox) {
        return userService.getOrCreateByOpenid(tokenBox.getOpenid());
    }

    @Override
    protected User getOrCreateByWxMpUser(WxMpUser wxMpUser) {
        return userService.getOrCreateByWxMpUser(wxMpUser);
    }

    @Override
    protected void saveUserContext(User user, HttpServletRequest httpServletRequest) {
        UserHolder.set(user);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        UserHolder.remove();
    }

}
