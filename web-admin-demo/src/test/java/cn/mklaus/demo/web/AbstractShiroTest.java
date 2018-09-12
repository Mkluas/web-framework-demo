package cn.mklaus.demo.web;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.*;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

/**
 * Abstract test case enabling Shiro in test environments.
 */
public abstract class AbstractShiroTest {

    protected static final String ADMIN_ACCOUNT = "admin";
    protected static final String ADMIN_PASSWORD = "12345678";

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private MockMvc mockMvc;

    protected MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        MockHttpSession session = new MockHttpSession(wac.getServletContext());
        mockMvc.perform(post("/auth/login")
                .session(session)
                .param("account", ADMIN_ACCOUNT)
                .param("password", ADMIN_PASSWORD))
                .andExpect(logicOk());
        mvc = MockMvcBuilders.webAppContextSetup(wac)
                .defaultRequest(post("/").session(session))
                .build();
    }

    static ResultMatcher logicOk() {
        return jsonPath("errCode").value(0);
    }

    static ResultMatcher errCode(int errCode) {
        return jsonPath("errCode").value(errCode);
    }

}