package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.dto.UserDTO;
import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.UserService;
import cn.mklaus.framework.base.BaseServiceImpl;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.util.Langs;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.lang.Lang;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author klaus
 * @date 2018/9/4 下午9:57
 */
@Service
public class UserServiceImpl extends BaseServiceImpl<User> implements UserService {

    @Override
    public ServiceResult register(String mobile, String password) {
        if (getByMobile(mobile) != null) {
            return ServiceResult.error("手机号已被注册");
        }

        User user = new User();
        user.setMobile(mobile);
        String salt = Langs.uuid();
        user.setSalt(salt);
        user.setPassword(md5Password(password, salt));
        insert(user);

        return ServiceResult.ok();
    }

    @Override
    public ServiceResult login(String mobile, String password) {
        User loginUser = getByMobile(mobile);
        if (loginUser == null) {
            return ServiceResult.error("手机号码或密码不正确");
        }

        if (validPassword(loginUser, password)) {
            return ServiceResult.ok().putEntity(new UserDTO(loginUser));
        }

        return ServiceResult.error("手机号码或密码不正确");
    }

    @Override
    public ServiceResult getUserInfo(String userId) {
        User user = fetch(userId);
        Assert.notNull(user, "用户ID不存在:" + userId);
        return ServiceResult.ok().put("user", new UserDTO(user));
    }

    @Override
    public ServiceResult passwd(String userId, String oldPassword, String newPassword) {
        User user = fetch(userId);
        Assert.notNull(user, "用户ID不存在:" + userId);
        if (validPassword(user, oldPassword)) {
            String newMd5Password = md5Password(newPassword, user.getSalt());
            update(Chain.make("password", newMd5Password), Cnd.where("user_id", "=", userId));
            return ServiceResult.ok();
        }
        return ServiceResult.error("原密码不正确");
    }

    @Override
    public ServiceResult resetPassword(String mobile, String newPassword) {
        User user = getByMobile(mobile);
        Assert.notNull(user, "该手机号用户未注册");
        String newMd5Password = md5Password(newPassword, user.getSalt());
        update(Chain.make("password", newMd5Password), Cnd.where("mobile", "=", mobile));
        return ServiceResult.ok();
    }

    private User getByMobile(String mobile) {
        return fetch(Cnd.where("mobile", "=", mobile));
    }

    private boolean validPassword(User user, String password) {
        String md5Password = md5Password(password, user.getSalt());
        return md5Password.equals(user.getPassword());
    }

    private String md5Password(String password, String salt) {
        return Lang.md5(password + salt);
    }

}