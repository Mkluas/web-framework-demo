package cn.mklaus.demo.service.impl;

import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.UserService;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * @author klaus
 * @date 2018/12/19 10:34 AM
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @Before
    public void setUp() throws Exception {

    }

    @org.junit.Test
    public void register() {
        userService.register("18027247255", "12345678");
    }

    @org.junit.Test
    public void getUserInfo() {
        User user = userService.fetch(1);
        System.out.println("user = " + user);
    }


}