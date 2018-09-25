package cn.mklaus.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Mklaus
 * @date 2018-08-15 下午4:52
 */
@Controller
@RequestMapping("demo")
public class DemoController {

    @GetMapping("qiniu")
    public String qiniu() {
        return "demo/qiniu";
    }

    @GetMapping("code")
    public String code() {
        return "demo/code";
    }

}
