package cn.mklaus.demo.web;

import cn.mklaus.demo.dto.AdminDTO;
import cn.mklaus.demo.service.AdminService;
import cn.mklaus.demo.vo.*;
import cn.mklaus.framework.bean.Pagination;
import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.bean.ServiceResult;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


/**
 * @author Klaus
 * @date 2018/7/5
 */
@Api(value = "admin", description = "管理员API")
@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @ApiOperation(value = "获取指定ID的管理员信息")
    @GetMapping("get")
    public JSONObject getAdmin(@ApiParam(name = "管理员id", example = "1") @RequestParam Integer adminId) {
        AdminDTO adminDTO = adminService.getAdmin(adminId);
        return Response.ok().put("admin", adminDTO).build();
    }

    @ApiOperation(value = "获取管理员列表")
    @GetMapping("list")
    public JSONObject listAdmin(@Valid PageVO pageVO) {
        Pagination pagination = adminService.listAdmin(pageVO);
        return Response.ok().put("pager", pagination).build();
    }

    @ApiOperation(value = "添加管理员")
    @RequiresRoles("admin")
    @PostMapping("save")
    public JSONObject saveAdmin(@Validated(Save.class) AdminVO adminVO) {
        ServiceResult result = adminService.saveAdmin(adminVO);
        return Response.withResult(result).build();
    }

    @ApiOperation(value = "更新管理员")
    @RequiresRoles("admin")
    @PostMapping("update")
    public JSONObject updateAdmin(@Validated(Update.class) AdminVO adminVO) {
        ServiceResult result = adminService.updateAdmin(adminVO);
        return Response.withResult(result).build();
    }

    @ApiOperation(value = "修改密码")
    @PostMapping(value = "passwd")
    public JSONObject passwd(@Valid PasswdVO passwdVO) {
        ServiceResult result = adminService.passwd(passwdVO);
        return Response.withResult(result).build();
    }

    @ApiOperation(value = "重置密码")
    @RequiresRoles("admin")
    @PostMapping(value = "resetpassword")
    public JSONObject resetPassword(@ApiParam(name = "管理员id", example = "1") @RequestParam Integer adminId) {
        ServiceResult result = adminService.resetPassword(adminId);
        return Response.withResult(result).build();
    }

    @ApiOperation(value = "根据id删除管理员")
    @RequiresRoles("admin")
    @PostMapping("remove")
    public JSONObject removeAdmin(@ApiParam(name = "管理员id", example = "1") @RequestParam Integer adminId) {
        ServiceResult result = adminService.removeAdmin(adminId);
        return Response.withResult(result).build();
    }

}
