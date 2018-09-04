package cn.mklaus.demo.vo;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @author Mklaus
 * @date 2018-08-06 下午12:09
 */
@Data
@ToString
public class ResetPasswordVO {

    @NotBlank(message = "手机号码不能为空")
    private String mobile;

    @NotBlank(message = "密码不能为空")
    private String password;

    @NotBlank(message = "验证码不能为空")
    private String verificationCode;

}
