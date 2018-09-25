package cn.mklaus.demo.web;

import cn.mklaus.demo.service.VerificationCodeService;
import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.bean.ServiceResult;
import cn.mklaus.framework.support.Qinius;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mklaus
 * @date 2018-03-29 下午2:41
 */
@RestController
@RequestMapping("api")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @Autowired
    private VerificationCodeService codeService;

    @GetMapping("qiniu/token")
    public JSONObject uptoken() {
        return Response.ok()
                .put("action", Qinius.getAction())
                .put("token", Qinius.getUpToken())
                .put("bucket_url", Qinius.getBucketUrl())
                .build();
    }

    @PostMapping("code/send")
    public JSONObject sendCode(@RequestParam String mobile) {
        ServiceResult result = codeService.sendVerificationCode(mobile);
        return Response.withResult(result).build();
    }

    @PostMapping("code/valid")
    public JSONObject validCode(@RequestParam String mobile, @RequestParam String code) {
        boolean valid = codeService.validVerificationCode(mobile, code);
        if (valid) {
            return Response.ok().build();
        }
        return Response.error("验证码不正确").build();
    }

}
