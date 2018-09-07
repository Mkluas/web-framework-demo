package cn.mklaus.demo.web;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.web.subject.WebSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Abstract test case enabling Shiro in test environments.
 */
public abstract class AbstractShiroTest {

    protected static final String ADMIN_ACCOUNT  = "admin";
    protected static final String ADMIN_PASSWORD = "12345678";

    protected static final String TEST_ACCOUNT  = "test";
    protected static final String TEST_PASSWORD = "12345678";

    @Autowired
    protected WebApplicationContext wac;
    @Autowired
    private SecurityManager securityManager;

    private static HashMap<String, MockHttpSession> sessionHashMap = new HashMap<>();

    protected MockHttpSession getHttpSessionWithShiroAuthenticationInfo() {
        List<String> roles = Arrays.asList("admin");
        List<String> permissions = Arrays.asList("admin");
        return getHttpSessionWithShiroAuthenticationInfo(roles, permissions);
    }

    protected MockHttpSession getHttpSessionWithShiroAuthenticationInfo(String roles, String permissions) {
        List<String> roleList = Arrays.asList(roles.split(","));
        List<String> permissionList = Arrays.asList(permissions.split(","));
        return getHttpSessionWithShiroAuthenticationInfo(roleList, permissionList);
    }

    protected MockHttpSession getHttpSessionWithShiroAuthenticationInfo(List<String> roles, List<String> permissions) {
        String key = roles.toString()+permissions.toString();
        MockHttpSession mockHttpSession = sessionHashMap.get(key);
        if (mockHttpSession != null) {
            return mockHttpSession;
        }

        DefaultSecurityManager securityManager = (DefaultSecurityManager) this.securityManager;
        SecurityUtils.setSecurityManager(securityManager);

        mockHttpSession = new MockHttpSession(wac.getServletContext());
        MockHttpServletRequest request = new MockHttpServletRequest(wac.getServletContext());
        request.setSession(mockHttpSession);
        MockHttpServletResponse response = new MockHttpServletResponse();

        WebSubject subject = new WebSubject.Builder(request, response).buildWebSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(ADMIN_ACCOUNT, ADMIN_PASSWORD);
        subject.login(token);

        sessionHashMap.put(key, mockHttpSession);
        return mockHttpSession;
    }

    static ResultMatcher logicOk() {
        return jsonPath("errCode").value(0);
    }

    static ResultMatcher errCode(int errCode) {
        return jsonPath("errCode").value(errCode);
    }

}