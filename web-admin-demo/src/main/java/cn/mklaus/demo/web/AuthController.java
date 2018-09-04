package cn.mklaus.demo.web;

import cn.mklaus.demo.service.AuthService;
import cn.mklaus.demo.vo.LoginVO;
import cn.mklaus.framework.bean.BaseErrorEnum;
import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.bean.ServiceResult;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import static org.apache.shiro.web.util.WebUtils.SAVED_REQUEST_KEY;

/**
 * @author Klaus
 * @date 2018/7/5
 */
@Api(description = "认证API")
@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Shiro拦截未登录时，重定向到/auth/error
     * 返回重新登录提示给用户
     * @return
     */
    @ApiIgnore
    @GetMapping("error")
    public JSONObject notLogin(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = getSavedRequest(req).getRequestURI();
        resp.setStatus(HttpStatus.UNAUTHORIZED.value());
        return Response.error(BaseErrorEnum.ACCOUNT_UNAUTHENTICATED)
                .put("requestURI", requestURI)
                .build();
    }

    @ApiIgnore
    @GetMapping("403")
    public JSONObject noPermission(HttpServletRequest req, HttpServletResponse resp) {
        String requestURI = getSavedRequest(req).getRequestURI();
        resp.setStatus(HttpStatus.FORBIDDEN.value());
        return Response.error(BaseErrorEnum.PERMISSION_DENIED)
                .put("requestURI", requestURI)
                .build();
    }


    private static SavedRequest getSavedRequest(ServletRequest request) {
        SavedRequest savedRequest = null;
        Subject subject = SecurityUtils.getSubject();
        Session session = subject.getSession(false);
        if (session != null) {
            savedRequest = (SavedRequest) session.getAttribute(SAVED_REQUEST_KEY);
        }
        return savedRequest;
    }

    @ApiOperation(value = "管理员登录")
    @PostMapping("login")
    public JSONObject login(@Valid LoginVO loginVO) {
        ServiceResult result = authService.login(loginVO);
        return Response.withResult(result).build();
    }

    @ApiOperation(value = "管理员登出")
    @PostMapping("logout")
    public JSONObject logout() {
        SecurityUtils.getSubject().logout();
        return Response.ok().build();
    }

}
