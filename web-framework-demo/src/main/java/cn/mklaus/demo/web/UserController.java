package cn.mklaus.demo.web;

import cn.mklaus.demo.conf.WebUserInterceptor;
import cn.mklaus.demo.dto.UserDTO;
import cn.mklaus.demo.service.UserService;
import cn.mklaus.demo.service.VerificationCodeService;
import cn.mklaus.demo.vo.LoginVO;
import cn.mklaus.demo.vo.PasswdVO;
import cn.mklaus.demo.vo.RegisterVO;
import cn.mklaus.demo.vo.ResetPasswordVO;
import cn.mklaus.framework.bean.BaseErrorEnum;
import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.bean.ServiceResult;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


/**
 * @author Mklaus
 * @date 2018-03-29 上午11:52
 */
@Controller
@RequestMapping("user")
public class UserController {


    @Autowired
    private UserService userService;
    @Autowired
    private VerificationCodeService verificationCodeService;

    @PostMapping("register")
    @ResponseBody
    public JSONObject register(@Validated RegisterVO registerVO) {
        if (verificationCodeService.validVerificationCode(registerVO.getMobile(), registerVO.getVerificationCode())) {
            ServiceResult result = userService.register(registerVO.getMobile(), registerVO.getPassword());
            return Response.withResult(result).build();
        }
        return Response.error(BaseErrorEnum.VERIFICATION_CODE_IS_INVALID).build();
    }

    @GetMapping("login")
    public String login() {
        return "user/login";
    }

    @PostMapping("login")
    @ResponseBody
    public JSONObject login(@Valid LoginVO loginVO, HttpSession session) {
        ServiceResult result = userService.login(loginVO.getMobile(), loginVO.getPassword());
        if (result.isOk()) {
            UserDTO userDTO = result.getEntity(UserDTO.class);
            session.setAttribute(WebUserInterceptor.SESSION_USER_ID_KEY, userDTO.getUserId());
            return Response.ok().put("user", userDTO).build();
        }
        return Response.withResult(result).build();
    }

    @PostMapping("logout")
    @ResponseBody
    public JSONObject logout(HttpSession session) {
        session.removeAttribute(WebUserInterceptor.SESSION_USER_ID_KEY);
        return Response.ok().build();
    }

    @GetMapping("info")
    @ResponseBody
    public JSONObject info(@ModelAttribute("userId") String userId) {
        ServiceResult result = userService.getUserInfo(userId);
        return Response.withResult(result).build();
    }

    @PostMapping("passwd")
    @ResponseBody
    public JSONObject passwd(@Validated PasswdVO passwdVO, @ModelAttribute("userId") String userId) {
        userService.passwd(userId, passwdVO.getOldPassword(), passwdVO.getNewPassword());
        return Response.ok().build();
    }

    @PostMapping("reset_password")
    @ResponseBody
    public JSONObject resetPassword(@Validated ResetPasswordVO resetPasswordVO) {
        if (verificationCodeService.validVerificationCode(resetPasswordVO.getMobile(), resetPasswordVO.getVerificationCode())) {
            ServiceResult result = userService.resetPassword(resetPasswordVO.getMobile(), resetPasswordVO.getPassword());
            return Response.withResult(result).build();
        }
        return Response.error(BaseErrorEnum.VERIFICATION_CODE_IS_INVALID).build();
    }


}
