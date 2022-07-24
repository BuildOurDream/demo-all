package demo.h2.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jingxin
 * @date 2022-07-24
 */
@RestController
public class MsgController {

    @Value("${os.name:123}")
    private String msg;

    @GetMapping("/msg")
    public String getMsg() {
        return msg;
    }
}
