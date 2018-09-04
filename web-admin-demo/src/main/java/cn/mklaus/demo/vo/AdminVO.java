package cn.mklaus.demo.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Klaus
 * @date 2018/7/5
 */
@ApiModel
@Data
@NoArgsConstructor
@ToString
public class AdminVO {

    @ApiModelProperty(name = "adminId,（新增不填，更新必填）", example = "1")
    @NotNull(groups = {Update.class}, message = "adminId不能为空")
    private Integer adminId;

    @ApiModelProperty(name = "账号", required = true)
    @NotBlank(groups = {Save.class, Update.class}, message = "帐号不能为空")
    @Pattern(groups = {Save.class, Update.class}, regexp = "[0-9a-z]+", message = "仅支持字母、数字（建议为姓名拼音）")
    private String account;

    @ApiModelProperty(name = "用户名", required = true)
    @NotBlank(groups = {Save.class, Update.class}, message = "姓名不能为空")
    @Pattern(regexp = "[\\u4e00-\\u9fa5]+", message = "姓名必须是简体中文", groups = {Save.class, Update.class})
    private String username;

    @ApiModelProperty(name = "手机号码", required = true)
    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 11, max = 11, message = "手机号的长度必须是11位", groups = {Save.class, Update.class})
    private String mobile;

    @ApiModelProperty(name = "邮箱")
    @Email(groups = {Save.class, Update.class}, message = "不符合邮箱格式")
    private String email;

}
