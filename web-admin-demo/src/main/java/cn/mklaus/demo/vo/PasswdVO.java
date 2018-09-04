package cn.mklaus.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;


/**
 * @author Mklaus
 * @date 2018-07-06 下午12:09
 */
@ApiModel
@Data
@ToString
public class PasswdVO {

    @ApiModelProperty(required = true, name = "原密码")
    @NotBlank(message = "原密码不能为空")
    private String oldPassword;

    @ApiModelProperty(required = true, name = "新密码")
    @NotBlank(message = "新密码不能为空")
    @Length(min = 6, max = 32)
    private String newPassword;

}
