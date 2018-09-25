package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.conf.OrderNoEnum;
import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.entity.Withdraw;
import cn.mklaus.demo.service.WithdrawService;
import cn.mklaus.framework.base.BaseServiceImpl;
import cn.mklaus.framework.bean.ServiceResult;
import com.github.binarywang.wxpay.bean.entpay.EntPayRequest;
import com.github.binarywang.wxpay.bean.entpay.EntPayResult;
import com.github.binarywang.wxpay.bean.request.WxPaySendRedpackRequest;
import com.github.binarywang.wxpay.bean.result.WxPaySendRedpackResult;
import com.github.binarywang.wxpay.exception.WxPayException;
import com.github.binarywang.wxpay.service.WxPayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author klaus
 * @date 2018/9/25 3:03 PM
 */
@Service
public class WithdrawServiceImpl extends BaseServiceImpl<Withdraw> implements WithdrawService {


    @Qualifier("wxMpPayService")
    @Autowired
    private WxPayService wxPayService;

    @Override
    public ServiceResult withdraw(User user, BigDecimal money) {
        if (money.compareTo(BigDecimal.ONE) < 0) {
            return ServiceResult.error("根据微信相关规则，不足一元不能发起提现");
        }

        Withdraw withdraw = new Withdraw();
        withdraw.setUserId(user.getId());
        withdraw.setOpenid(user.getOpenid());
        withdraw.setMoney(money);
        withdraw.setPartnerTradeNo(OrderNoEnum.PARTNER_TRADE_NO.next());
        withdraw.setDone(true);
        withdraw.setMsg("ok");
        insert(withdraw);

        EntPayRequest entPayRequest = EntPayRequest.newBuilder()
                .amount(withdraw.getTotalMoney())
                .checkName("NO_CHECK")
                .spbillCreateIp("127.0.0.1")
                .openid(withdraw.getOpenid())
                .partnerTradeNo(withdraw.getPartnerTradeNo())
                .description("余额提现 - 雪花啤酒")
                .build();

        try {
            EntPayResult result = wxPayService.getEntPayService().entPay(entPayRequest);
        } catch (WxPayException e) {
            withdraw.setDone(false);
            withdraw.setMsg(e.toString());
            update(withdraw);
            return ServiceResult.error(e.toString());
        }

        return ServiceResult.ok();
    }

    @Override
    public ServiceResult sendRedpack(User user, BigDecimal money) {
        if (money.compareTo(BigDecimal.ONE) < 0) {
            return ServiceResult.error("根据微信相关规则，不足一元不能发起提现");
        }

        WxPaySendRedpackRequest redpackRequest = WxPaySendRedpackRequest.newBuilder()
                .mchBillNo(OrderNoEnum.MCH_BILL_NO.next())
                .sendName("发送者")
                .reOpenid(user.getOpenid())
                .totalAmount(getTotalMoney(money))
                .totalNum(1)
                .wishing("祝福语")
                .clientIp("127.0.0.1")
                .actName("活动名称")
                .remark("备注")
                .build();

        try {
            WxPaySendRedpackResult result = wxPayService.sendRedpack(redpackRequest);
        } catch (WxPayException e) {
            e.printStackTrace();
            return ServiceResult.error(e.toString());
        }

        return ServiceResult.ok();
    }

    public int getTotalMoney(BigDecimal money) {
        return money.multiply(BigDecimal.valueOf(100)).intValue();
    }

}