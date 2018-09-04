package cn.mklaus.demo.service;

import cn.mklaus.demo.entity.User;
import cn.mklaus.framework.base.BaseService;
import cn.mklaus.framework.bean.ServiceResult;

/**
 * @author klaus
 * @date 2018/9/4 下午9:57
 */
public interface UserService extends BaseService<User> {

    ServiceResult register(String mobile, String password);

    ServiceResult login(String mobile, String password);

    ServiceResult getUserInfo(String userId);

    ServiceResult passwd(String userId, String oldPassword, String newPassword);

    ServiceResult resetPassword(String mobile, String newPassword);

}