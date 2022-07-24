package demo.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author jingxin
 * @date 2022-07-17
 */
@Controller
public class PageController {

    @GetMapping("/hello")
    public String hello(Model model) {
        model.addAttribute("msg", "欢迎使用");
        model.addAttribute("url", "http://www.baidu.com");
        return "success";
    }

    @GetMapping("/main")
    public String main(Model model) {
        model.addAttribute("name", "逍遥生");
        return "main";
    }
}
