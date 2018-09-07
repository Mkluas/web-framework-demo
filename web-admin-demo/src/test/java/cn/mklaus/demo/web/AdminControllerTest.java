package cn.mklaus.demo.web;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author klaus
 * @date 2018/9/7 上午11:44
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminControllerTest extends AbstractShiroTest {

    private MockHttpSession session;
    private Integer adminId;

    @Autowired
    protected MockMvc mvc;

    @Before
    public void setUp() {
        session = getHttpSessionWithShiroAuthenticationInfo();
    }

    @After
    public void tearDown() {

    }

    // 加上注解回滚
    @Transactional
    @Rollback
    @Test
    public void saveAdmin() throws Exception {
        String account = "klaus";
        String username = "谢先生";
        String mobile = "18027247247";
        String email = "xie.jinye@163.com";
        MockHttpServletRequestBuilder save = post("/admin/save")
                .session(this.session)
                .param("account", account)
                .param("username", username)
                .param("mobile", mobile)
                .param("email", email);

        MvcResult result = mvc.perform(save)
                .andDo(print())
                .andExpect(jsonPath("admin.account").value(account))
                .andExpect(jsonPath("admin.username").value(username))
                .andExpect(jsonPath("admin.mobile").value(mobile))
                .andExpect(jsonPath("admin.email").value(email))
                .andExpect(jsonPath("admin.id").isNumber())
                .andExpect(logicOk())
                .andReturn();

        JSONObject data = JSON.parseObject(result.getResponse().getContentAsString());
        this.adminId = data.getJSONObject("admin").getInteger("id");
    }

    @Test
    public void listAdmin() throws Exception {
        mvc.perform(get("/admin/list").session(session))
                .andDo(print())
                .andExpect(status().isOk());
    }

}