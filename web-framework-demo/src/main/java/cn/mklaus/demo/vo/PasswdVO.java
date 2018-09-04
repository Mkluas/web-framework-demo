package cn.mklaus.demo.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @author Mklaus
 * @date 2018-08-06 上午11:36
 */
@Data
@ToString
public class PasswdVO {

    private String oldPassword;

    private String newPassword;

}
