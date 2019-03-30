package cn.mklaus.demo.entity;

import cn.mklaus.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.nutz.dao.entity.annotation.*;

/**
 * @author klaus
 * @date 2018/9/4 下午6:25
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
@Table("t_user")
public class User extends BaseEntity {

    @Column
    @Default("")
    private String unionid;

    @Column
    @Default("")
    private String openid;

    @Column
    @Default("")
    @ColDefine(width = 255)
    private String headimgurl;

    @Column
    @Default("")
    @ColDefine(customType = "varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")
    private String nickname;

    @Column
    @Default("0")
    @Comment("1男 2女 0未知")
    private Integer sex;

    @Column
    @Default("")
    private String country;

    @Column
    @Default("")
    private String province;

    @Column
    @Default("")
    private String city;

    @Column
    @Default("0")
    private Boolean subscribe;

    /**
     * 小程序
     */

    @Column
    @Default("")
    private String sessionKey;

    @Column
    @Default("")
    private String token;

}
