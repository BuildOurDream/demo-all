package demo.springsecurity.controller;

import cn.hutool.json.JSONUtil;
import demo.springsecurity.entity.UserEntity;
import demo.springsecurity.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

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
//    @Secured({"ROLE_admin"})
//    @PreAuthorize("hasAnyAuthority('admin2')")
//    @PostAuthorize("hasAnyAuthority('admin2')")
    //对入参进行过滤
//    @PreFilter("filterObject.account == 'admin'")
    //对返回数据进行过滤
//    @PostFilter("filterObject.account == 'admin'")
    public List<UserEntity> secure() {
        System.out.println("testSecure");
        return userService.list();
    }

}
