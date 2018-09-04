package cn.mklaus.demo.entity;

import cn.mklaus.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Mklaus
 * @date 2018-07-31 上午10:39
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Table("t_verification_code")
public class VerificationCode extends BaseEntity {

    @Column
    @Default("")
    private String mobile;

    @Column
    @Default("")
    private String code;

    @Column("is_success")
    @Default("0")
    private Boolean success;

    @Column
    @Default("ok")
    private String msg;

}

