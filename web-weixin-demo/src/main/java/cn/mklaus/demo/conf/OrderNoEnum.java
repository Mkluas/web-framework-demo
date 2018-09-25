package cn.mklaus.demo.conf;

import cn.mklaus.framework.util.Langs;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author Mklaus
 * @date 2018-03-16 下午4:56
 */
public enum OrderNoEnum {

    /**
     * 订单支付
     */
    OUT_TRADE_NO("100", 7),

    /**
     * 订单退款
     */
    OUT_REFUND_NO("200", 7),

    /**
     * 企业付款
     */
    PARTNER_TRADE_NO("300", 7),

    /**
     * 现金红包
     */
    MCH_BILL_NO("400", 7)

    ;


    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");
    static {
        SIMPLE_DATE_FORMAT.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
    }

    private String prefix;
    private int suffixLength;

    OrderNoEnum(String prefix, int suffixLength) {
        this.prefix = prefix;
        this.suffixLength = suffixLength;
    }

    public String next() {
        synchronized (SIMPLE_DATE_FORMAT) {
            return this.prefix + SIMPLE_DATE_FORMAT.format(new Date()) + Langs.numberStr(suffixLength);
        }
    }

}
