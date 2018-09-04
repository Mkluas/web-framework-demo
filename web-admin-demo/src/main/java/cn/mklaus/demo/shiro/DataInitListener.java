package cn.mklaus.demo.shiro;


import cn.mklaus.demo.entity.Admin;
import cn.mklaus.demo.service.AdminService;
import cn.mklaus.demo.vo.AdminVO;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;
import org.nutz.log.Logs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author Mklaus
 * @date 2018-02-27 下午4:14
 */
@Service
public class DataInitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private Dao dao;
    @Autowired
    private AdminService adminService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null) {
            initAdminData();
        }
    }

    private void initAdminData() {
        if (dao.count(Admin.class, Cnd.format("account = 'admin'")) == 0) {
            Logs.get().info("Init admin data");

            AdminVO adminVO = new AdminVO();
            adminVO.setAccount("admin");
            adminVO.setUsername("超级管理员");
            adminVO.setEmail("admin@zimple.com");
            adminVO.setMobile("18888888888");
            adminService.saveAdmin(adminVO);
        }
    }

}

