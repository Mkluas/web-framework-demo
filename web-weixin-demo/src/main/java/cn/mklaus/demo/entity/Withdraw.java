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
 * @date 2018/9/25 上午10:58
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Table("t_withdraw")
public class Withdraw extends BaseEntity {

    @Column(hump = true)
    @Default("0")
    private Integer userId;

    @Column
    @Default("")
    private String openid;

    @Column(hump = true)
    @Default("")
    private String partnerTradeNo;

    @Column
    @Default("0")
    @ColDefine(customType = "decimal(10,2)")
    private BigDecimal money;

    @Column(hump = true)
    @Default("ok")
    @ColDefine(width = 255)
    private String msg;

    @Column
    @Default("0")
    private Boolean done;

    public int getTotalMoney() {
        return this.money.multiply(BigDecimal.valueOf(100)).intValue();
    }


}
