package cn.mklaus.demo.dto;

import cn.mklaus.demo.entity.Admin;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mklaus
 * @date 2018-07-11 上午10:36
 */
@Data
@ToString
public class AdminDTO {

    public AdminDTO(Admin admin) {
        this.id = admin.getId();
        this.account = admin.getAccount();
        this.username = admin.getUsername();
        this.mobile = admin.getMobile();
        this.email = admin.getEmail();
        this.createTime = admin.getCreateTime();
        this.updateTime = admin.getUpdateTime();
    }

    private Integer id;

    private String account;

    private String username;

    private String mobile;

    private String email;

    private Integer createTime;

    private Integer updateTime;

    public static List<AdminDTO> toList(List adminList) {
        List<AdminDTO> dtoList = new ArrayList<>();
        for(Object o : adminList) {
            dtoList.add(new AdminDTO((Admin) o));
        }
        return dtoList;
    }

}
