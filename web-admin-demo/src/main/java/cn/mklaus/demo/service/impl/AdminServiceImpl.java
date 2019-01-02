package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.conf.Config;
import cn.mklaus.demo.dto.AdminDTO;
import cn.mklaus.demo.entity.Admin;
import cn.mklaus.demo.service.AdminService;
import cn.mklaus.demo.vo.AdminVO;
import cn.mklaus.demo.vo.PasswdVO;
import cn.mklaus.framework.base.BaseServiceImpl;
import cn.mklaus.framework.bean.BaseErrorEnum;
import cn.mklaus.framework.bean.PageVO;
import cn.mklaus.framework.bean.Pagination;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.util.Langs;
import cn.mklaus.framework.util.Times;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.nutz.dao.FieldMatcher;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

/**
 * @author Klaus
 * @date 2018/7/5
 */
@Service
public class AdminServiceImpl extends BaseServiceImpl<Admin> implements AdminService {

    @Override
    public AdminDTO getAdmin(int adminId) {
        Admin fetch = fetch(adminId);
        return fetch == null ? null : new AdminDTO(fetch);
    }

    @Override
    public ServiceResult saveAdmin(AdminVO adminVO) {
        if (fetchByAccount(adminVO.getAccount()) != null) {
            return ServiceResult.error(BaseErrorEnum.ACCOUNT_ALREADY_EXIST);
        }

        String salt = Langs.uuid();
        String passwordHash = this.hashPassword(adminVO.getAccount(), salt, Config.ADMIN_DEFAULT_PASSWORD);

        Admin admin = Admin.builder()
                .account(adminVO.getAccount())
                .username(adminVO.getUsername())
                .mobile(adminVO.getMobile())
                .email(adminVO.getEmail())
                .salt(salt)
                .password(passwordHash)
                .build();

        insert(admin);
        return ServiceResult.ok().put("admin", new AdminDTO(admin));
    }

    @Override
    public Pagination listAdmin(PageVO pageVO) {
        Condition condition = Strings.isBlank(pageVO.getKey()) ?
                null :
                Cnd.where("username|account|mobile", "like", wrapSearchKey(pageVO.getKey()));

        FieldMatcher matcher = FieldMatcher.make("", "^password|salt$", true);
        Pagination pagination = listPage(condition, matcher, pageVO.toPager());
        pagination.setList(AdminDTO.toList(pagination.getList()));

        return pagination;
    }

    @Override
    public ServiceResult updateAdmin(AdminVO adminVO) {
        Admin admin = fetch(adminVO.getAdminId());
        Assert.notNull(admin, "管理员不存在: " + adminVO.getAdminId());

        if (!admin.getAccount().equals(adminVO.getAccount())) {
            if (fetchByAccount(adminVO.getAccount()) != null) {
                return ServiceResult.error(BaseErrorEnum.ACCOUNT_ALREADY_EXIST);
            }
        }

        Admin updateAdmin = Admin.builder()
                .account(adminVO.getAccount())
                .username(adminVO.getUsername())
                .mobile(adminVO.getMobile())
                .email(adminVO.getEmail())
                .build();

        updateAdmin.setId(admin.getId());
        updateAdmin.setUpdateTime(Times.now());
        updateIgnoreNull(updateAdmin);

        return ServiceResult.ok().put("admin", new AdminDTO(updateAdmin));
    }

    @Override
    public ServiceResult removeAdmin(int adminId) {
        Admin creator = (Admin) SecurityUtils.getSubject().getPrincipal();
        if (adminId == creator.getId()) {
            return ServiceResult.error("不能删除自己");
        }

        Admin deleteAdmin = fetch(adminId);
        Assert.notNull(deleteAdmin, "管理员不存在: " + adminId);

        if (Config.ADMIN_ROOT_ACCOUNT.equals(deleteAdmin.getAccount())) {
            return ServiceResult.error("不能删除超级管理员");
        }

        delete(deleteAdmin);
        return ServiceResult.ok();
    }

    @Override
    public ServiceResult passwd(PasswdVO passwdVO) {
        Admin currentAdmin = (Admin) SecurityUtils.getSubject().getPrincipal();
        String account = currentAdmin.getAccount();
        String salt = currentAdmin.getSalt();

        String oldPasswordHash = this.hashPassword(account, salt, passwdVO.getOldPassword());
        if (!oldPasswordHash.equals(currentAdmin.getPassword())) {
            return ServiceResult.error(BaseErrorEnum.WRONG_PASSWORD);
        }

        String newPasswordHash = this.hashPassword(account, salt, passwdVO.getNewPassword());
        update(Chain.make("password", newPasswordHash), Cnd.where("account", "=", account));
        return ServiceResult.ok();
    }

    @Override
    public ServiceResult resetPassword(int adminId) {
        Admin admin = fetch(adminId);
        Assert.notNull(admin, "管理员不存在: " + adminId);

        String account = admin.getAccount();
        String salt = admin.getSalt();
        String newPasswordHash = this.hashPassword(account, salt, Config.ADMIN_DEFAULT_PASSWORD);
        update(Chain.make("password", newPasswordHash), Cnd.where("id", "=", adminId));

        return ServiceResult.ok();
    }

    private Admin fetchByAccount(String account) {
        return fetch(Cnd.where("account", "=", account));
    }

    private String hashPassword(String account, String salt, String password) {
        SimpleHash hash = new SimpleHash(Config.PASSWORD_HASH_ALGORITHM, password, account + salt, Config.PASSWORD_HASH_ITERATIONS);
        return hash.toHex();
    }

}
