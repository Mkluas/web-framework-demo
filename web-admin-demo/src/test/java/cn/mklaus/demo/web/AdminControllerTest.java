package cn.mklaus.demo.web;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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

    @Autowired
    protected MockMvc mvc;

    @Before
    public void setUp() {
        session = getHttpSessionWithShiroAuthenticationInfo();
    }

    @After
    public void tearDown() {

    }

    @Test
    public void listAdmin() throws Exception {
        mvc.perform(get("/admin/list").session(session))
                .andDo(print())
                .andExpect(status().isOk());
    }

}