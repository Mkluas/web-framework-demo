package cn.mklaus.demo.service;

import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.entity.Withdraw;
import cn.mklaus.framework.base.BaseService;
import cn.mklaus.framework.bean.ServiceResult;

import java.math.BigDecimal;

/**
 * @author klaus
 * @date 2018/9/25 3:00 PM
 */
public interface WithdrawService extends BaseService<Withdraw> {

    ServiceResult withdraw(User user, BigDecimal money);

    ServiceResult sendRedpack(User user, BigDecimal money);

}