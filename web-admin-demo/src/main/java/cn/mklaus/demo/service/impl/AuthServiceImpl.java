package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.service.AuthService;
import cn.mklaus.demo.vo.LoginVO;
import cn.mklaus.framework.bean.ServiceResult;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.stereotype.Service;

/**
 * @author Klaus
 * @date 2018/7/5
 */
@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public ServiceResult login(LoginVO loginVO) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginVO.getAccount(), loginVO.getPassword());
        SecurityUtils.getSubject().login(token);
        return ServiceResult.ok();
    }

}
