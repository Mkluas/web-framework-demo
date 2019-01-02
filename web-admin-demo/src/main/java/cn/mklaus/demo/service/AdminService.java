package cn.mklaus.demo.service;

import cn.mklaus.demo.dto.AdminDTO;
import cn.mklaus.demo.entity.Admin;
import cn.mklaus.demo.vo.AdminVO;
import cn.mklaus.demo.vo.PasswdVO;
import cn.mklaus.framework.base.BaseService;
import cn.mklaus.framework.bean.PageVO;
import cn.mklaus.framework.bean.Pagination;
import cn.mklaus.framework.bean.ServiceResult;

/**
 * @author Klaus
 * @date 2018/7/5
 */
public interface AdminService extends BaseService<Admin> {

    /**
     * 获取指定ID的管理员信息
     * @param adminId   管理员ID
     * @return
     */
    AdminDTO getAdmin(int adminId);

    /**
     * 添加管理员
     * @param adminVO   管理员信息VO
     * @return 业务结果
     */
    ServiceResult saveAdmin(AdminVO adminVO);

    /**
     * 获取管理员列表
     * PageVo
     * @return  分页与列表模型
     */
    Pagination listAdmin(PageVO pageVO);

    /**
     * 更新管理员信息
     * @param adminVO   管理员信息VO
     * @return 业务结果
     */
    ServiceResult updateAdmin(AdminVO adminVO);

    /**
     * 删除指定ID的管理员
     * @param adminId   管理员ID
     * @return  业务结果
     */
    ServiceResult removeAdmin(int adminId);

    /**
     * 修改管理员密码
     * @param passwdVO  封装新密码和旧密码的请求VO
     * @return  业务结果
     */
    ServiceResult passwd(PasswdVO passwdVO);

    /**
     * 重置管理员密码
     * @param adminId   管理员ID
     * @return  业务结果
     */
    ServiceResult resetPassword(int adminId);

}
