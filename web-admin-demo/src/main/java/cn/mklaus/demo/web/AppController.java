package cn.mklaus.demo.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author klaus
 * @date 2018/11/13 10:17 AM
 */
@Controller
@RequestMapping
public class AppController {

    @GetMapping("/")
    public String index(Model model) {
        return "home";
    }

    @GetMapping("/auth/login")
    public String login() {
        return "backend/login";
    }

    @GetMapping("/backend")
    public String backend() {
        return "redirect:/backend/admin";
    }

    @GetMapping("/backend/passwd")
    public String passwd() {
        return "/backend/passwd";
    }


}
