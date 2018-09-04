package cn.mklaus.demo.entity;

import cn.mklaus.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.nutz.dao.entity.annotation.*;

import java.math.BigDecimal;

/**
 * @author klaus
 * @date 2018/9/4 下午9:55
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Table("t_user")
public class User extends BaseEntity {

    @Column(hump = true)
    @Default("")
    @ColDefine(update = false)
    @Prev(@SQL("SELECT IFNULL(MAX(id),0) + 10000001 FROM t_user"))
    private String userId;

    @Column
    @ColDefine(customType = "decimal(10,2)")
    private BigDecimal money;


    @Column
    @Default("")
    private String username;

    @Column
    @Default("")
    private String mobile;

    @Column
    @Default("")
    private String password;

    @Column
    @Default("")
    private String salt;

}
