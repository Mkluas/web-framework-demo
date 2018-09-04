package cn.mklaus.demo.entity;

import cn.mklaus.framework.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Comment;
import org.nutz.dao.entity.annotation.Default;
import org.nutz.dao.entity.annotation.Table;

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
    private String headimgurl;

    @Column
    @Default("")
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
