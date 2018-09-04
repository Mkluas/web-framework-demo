package cn.mklaus.demo.shiro;

import cn.mklaus.demo.entity.Admin;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.lang.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;

/**
 * @author Mklaus
 * @date 2018-07-05 下午5:54
 */
public class AdminRealm extends AuthorizingRealm {

    private static final Logger log = LoggerFactory.getLogger(AdminRealm.class);

    @Resource
    private Dao dao;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.info("AdminRealm do get authorization info.");
        Admin admin = (Admin) super.getAvailablePrincipal(principals);
        SimpleAuthorizationInfo simpleAuthorInfo = new SimpleAuthorizationInfo();
        simpleAuthorInfo.addRole(admin.getAccount());
        simpleAuthorInfo.addStringPermission(admin.getAccount());
        return simpleAuthorInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        log.info("AdminRealm do get authentication info.");

        String account = token.getPrincipal().toString();
        if (Strings.isBlank(account)) {
            throw new AuthenticationException("账号为空");
        }
        Admin admin = dao.fetch(Admin.class, Cnd.where("account","=",account));
        if (admin == null) {
            throw new UnknownAccountException("账号或密码不正确");
        }
        return new SimpleAuthenticationInfo(admin, admin.getPassword(), ByteSource.Util.bytes(account + admin.getSalt()), getName());
    }

    @Override
    public String getName() {
        return "Admin";
    }

}
