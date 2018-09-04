package cn.mklaus.demo.service;

import cn.mklaus.demo.entity.VerificationCode;
import cn.mklaus.framework.base.BaseService;
import cn.mklaus.framework.bean.ServiceResult;

/**
 * @author Mklaus
 * @date 2018-07-31 上午10:46
 */
public interface VerificationCodeService extends BaseService<VerificationCode> {

    ServiceResult sendVerificationCode(String mobile);

    boolean validVerificationCode(String mobile, String code);

}

