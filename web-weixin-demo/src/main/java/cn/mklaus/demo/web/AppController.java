package cn.mklaus.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author klaus
 * @date 2018/9/4 下午6:29
 */
@Controller
@RequestMapping
public class AppController {

    @GetMapping
    public String index() {
        return "index";
    }

}
