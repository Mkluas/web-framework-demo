package cn.mklaus.demo.conf;

import cn.mklaus.demo.entity.User;
import cn.mklaus.demo.service.UserService;
import cn.mklaus.framework.bean.BaseErrorEnum;
import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.extend.AbstractTokenInterceptor;
import cn.mklaus.framework.util.Https;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author Mklaus
 * @date 2018-08-17 上午10:24
 */
public class UserTokenInterceptor extends AbstractTokenInterceptor<Integer> {

    public static final String USER_KEY = UserTokenInterceptor.class.getName() + ".USER_ID";

    @Autowired
    private UserService userService;

    @Override
    protected List<String> getUriList() {
        return Arrays.asList("/app/comment/save");
    }

    @Override
    protected List<String> getIgnoreUriList() {
        return Arrays.asList("/user/login");
    }

    @Override
    protected Integer extraToken(HttpServletRequest req) {
        Object userId = req.getSession().getAttribute(USER_KEY);
        return userId == null ? 0 : Integer.valueOf(userId.toString());
    }

    @Override
    protected boolean handleToken(Integer userId, HttpServletRequest req, HttpServletResponse resp) throws Exception {
        User user = userService.fetch(userId);
        if (user == null) {
            JSONObject response = Response.error(BaseErrorEnum.TOKEN_INVALID).build();
            Https.response(response.toJSONString(), resp);
            return false;
        }

        UserHolder.set(user);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
        UserHolder.remove();
    }

}
