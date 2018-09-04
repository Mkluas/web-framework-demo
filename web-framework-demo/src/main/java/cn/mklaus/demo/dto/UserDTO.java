package cn.mklaus.demo.dto;

import cn.mklaus.demo.entity.User;
import lombok.Data;
import lombok.ToString;

/**
 * @author klaus
 * @date 2018/9/4 下午9:58
 */
@Data
@ToString
public class UserDTO {

    public UserDTO(User user) {
        this.userId = user.getUserId();
        this.username = user.getUsername();
        this.mobile = user.getMobile();
    }

    private String userId;

    private String username;

    private String mobile;

}
