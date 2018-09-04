package cn.mklaus.demo.entity;

import cn.mklaus.framework.base.BaseEntity;
import lombok.*;
import org.nutz.dao.entity.annotation.ColDefine;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Table;

/**
 * @author Mklaus
 * @date 2018-08-15 上午11:35
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Builder
@Table("t_admin")
public class Admin extends BaseEntity {

    @Column
    @ColDefine(update = false)
    private String account;

    @Column
    private String password;

    @Column
    @ColDefine(update = false)
    private String salt;

    @Column
    @Default("")
    private String username;

    @Column
    @Default("")
    private String mobile;

    @Column
    @Default("")
    private String email;

}

