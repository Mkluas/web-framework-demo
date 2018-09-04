package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.entity.VerificationCode;
import cn.mklaus.demo.service.VerificationCodeService;
import cn.mklaus.framework.base.BaseServiceImpl;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.support.Sms;
import cn.mklaus.framework.util.Langs;
import cn.mklaus.framework.util.Times;
import org.nutz.dao.Cnd;
import org.nutz.dao.Condition;
import org.springframework.stereotype.Service;

/**
 * @author Mklaus
 * @date 2018-07-31 上午10:47
 */
@Service
public class VerificationCodeServiceImpl extends BaseServiceImpl<VerificationCode> implements VerificationCodeService {

    private static final String TEST_MOBILE = "18027247255";

    @Override
    public ServiceResult sendVerificationCode(String mobile) {
        String code;
        Sms.Result result;
        if (TEST_MOBILE.equals(mobile)) {
            code = "6666";
            result = new Sms.Result(true, "test");
        } else {
            if (sendSuccessOverThreeTimesInOneDay(mobile)) {
                return ServiceResult.error("一天之内发送短信上限");
            }
            code = Langs.numberStr(4);
            result = Sms.sendSms(mobile, code);
        }

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setMobile(mobile);
        verificationCode.setCode(code);
        verificationCode.setSuccess(result.isSendSuccess());
        verificationCode.setMsg(result.getErrMsg());
        insert(verificationCode);

        return result.isSendSuccess() ? ServiceResult.ok() : ServiceResult.error(result.getErrMsg());
    }

    @Override
    public boolean validVerificationCode(String mobile, String code) {
        int validStartTime = Times.now() - 10 * 60;
        Condition condition = Cnd.where("mobile", "=", mobile)
                .and("code", "=", code)
                .and("ct", ">", validStartTime);
        return count(condition) > 0;
    }

    private boolean sendSuccessOverThreeTimesInOneDay(String mobile) {
        int startTime = Times.startTimeOfTodayOnBeijinTime();
        Condition condition = Cnd.where("mobile", "=", mobile)
                .and("ct", ">", startTime)
                .and("is_success", "=", true);
        return count(condition) > 3;
    }

}

