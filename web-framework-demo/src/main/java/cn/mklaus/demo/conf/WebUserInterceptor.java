package cn.mklaus.demo.conf;

import cn.mklaus.framework.bean.BaseErrorEnum;
import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.extend.AbstractTokenInterceptor;
import cn.mklaus.framework.util.Https;
import com.alibaba.fastjson.JSONObject;
import org.nutz.lang.Strings;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mklaus
 * @date 2018-08-06 上午10:30
 */
public class WebUserInterceptor extends AbstractTokenInterceptor<String> {

    public static final String SESSION_USER_ID_KEY = "session_login_user_id";

    @Override
    protected List<String> getUriList() {
        return Arrays.asList("/user/info", "/user/logout", "/user/passwd");
    }

    @Override
    protected String extraToken(HttpServletRequest request) {
        Object loginUserId = request.getSession().getAttribute(SESSION_USER_ID_KEY);
        return loginUserId == null ? "" : loginUserId.toString();
    }

    @Override
    protected boolean handleToken(String userId, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        boolean isLogin = Strings.isNotBlank(userId);
        if (isLogin) {
            return true;
        } else {
            JSONObject response = Response.error(BaseErrorEnum.TOKEN_INVALID).errMsg("未登录或登录过期").build();
            Https.response(response.toJSONString(), httpServletResponse);
            return false;
        }
    }

}
