package cn.mklaus.demo.entity;

import cn.mklaus.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Table;

import java.math.BigDecimal;

/**
 * @author klaus
 * @date 2018/9/25 上午10:57
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Table("t_order")
public class Order extends BaseEntity {

    public enum Status {
        /**
         * 订单状态
         */
        NEW("待支付"),
        PAID("已支付"),
        CANCEL("已取消"),
        CONFIRM("确认订单"),
        REFUNDED("已退款"),
        FINISH("已完成"),
        ALL("全部")
        ;
        private String text;
        Status(String text) {
            this.text = text;
        }
        public String getText() {
            return text;
        }
    }

    @Column(hump = true)
    @Default("0")
    private Integer userId;

    @Column
    @Default("")
    private String openid;

    @Column
    @ColDefine(customType = "decimal(10,2)")
    private BigDecimal price;

    @Column
    @Default("")
    private String body;

    @Column(hump = true)
    @Default("")
    private String outTradeNo;

    @Column(hump = true)
    @Default("")
    private String transactionId;

    @Column(hump = true)
    @Default("")
    private String outRefundNo;

    @Column(hump = true)
    @Default("")
    private String refundId;

    @Column
    @Default("NEW")
    private Status status;

    @Column(hump = true)
    @Default("0")
    private Integer payTime;

    public int getTotalFee() {
        return this.price.multiply(BigDecimal.valueOf(100)).intValue();
    }

}
