package cn.mklaus.demo.web;

import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.support.Qinius;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author klaus
 * @date 2018/11/13 5:26 PM
 */
@RestController
@RequestMapping("api")
public class ApiController {

    @GetMapping("qiniu/token")
    public JSONObject uptoken() {
        return Response.ok()
                .put("action", "https://up-z2.qiniup.com")
                .put("token", Qinius.getUpToken())
                .put("bucket_url", Qinius.getBucketUrl())
                .build();
    }

}
