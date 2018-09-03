package cn.mklaus.demo;

import cn.mklaus.framework.bean.Response;
import cn.mklaus.framework.support.Qinius;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

/**
 * @author Mklaus
 * @date 2018-03-29 下午2:41
 */
@RestController
@RequestMapping("api")
public class ApiController {

    private static final Logger log = LoggerFactory.getLogger(ApiController.class);

    @GetMapping("qiniu/token")
    public JSONObject uptoken() {
        return Response.ok()
                .put("action", Qinius.getAction())
                .put("token", Qinius.getUpToken())
                .put("bucket_url", Qinius.getBucketUrl())
                .build();
    }

}
