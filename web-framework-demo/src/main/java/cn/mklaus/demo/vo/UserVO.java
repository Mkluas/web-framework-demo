package cn.mklaus.demo.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author Mklaus
 * @date 2018-08-06 上午10:02
 */
@Data
@ToString
public class UserVO {

    private String username;

    private String email;

    private String nickname;

    private String avatar;

}
