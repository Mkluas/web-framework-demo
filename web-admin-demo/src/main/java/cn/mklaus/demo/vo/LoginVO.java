package cn.mklaus.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author Klaus
 * @date 2018/7/5
 */
@ApiModel
@Data
@ToString
public class LoginVO {

    @ApiModelProperty(required = true, name = "账号")
    @NotBlank(message = "帐号不能为空")
    private String account;

    @ApiModelProperty(required = true, name = "密码")
    @NotBlank(message = "密码不能为空")
    private String password;

}
