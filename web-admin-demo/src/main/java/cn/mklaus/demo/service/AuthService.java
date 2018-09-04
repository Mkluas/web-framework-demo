package cn.mklaus.demo.service;

import cn.mklaus.demo.vo.LoginVO;
import cn.mklaus.framework.bean.ServiceResult;

/**
 * @author Klaus
 * @date 2018/7/5
 */
public interface AuthService {
    /**
     * 登录
     * @param loginVO 账号密码
     * @return
     */
    ServiceResult login(LoginVO loginVO);
}
