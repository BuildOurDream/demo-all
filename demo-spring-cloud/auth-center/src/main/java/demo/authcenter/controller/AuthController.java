package demo.authcenter.controller;

import demo.domain.BaseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-12
 */
@RestController
@RequestMapping("/auth")
public class AuthController {

    @GetMapping("/login")
    public BaseResult login() {
        System.out.println(Thread.currentThread().getName());
        return BaseResult.ok();
    }
}
