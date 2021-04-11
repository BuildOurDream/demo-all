package demo.springsecurity.controller;

import cn.hutool.json.JSONUtil;
import demo.springsecurity.service.UserService;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-03-20
 */
@RestController
public class TestController {

    @Resource
    private UserService userService;

    @GetMapping("/test")
    public Object test() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        System.out.println(JSONUtil.toJsonPrettyStr(authentication));
        return userService.list();
    }

    @GetMapping("/index")
    public Object index() {
        return "登录成功";
    }

    @GetMapping("/fail")
    public Object fail() {
        return "登录失败";
    }

    @GetMapping("/secure")
    @Secured({"ROLE_admin"})
    public Object secure() {
        return "secure test";
    }

}
