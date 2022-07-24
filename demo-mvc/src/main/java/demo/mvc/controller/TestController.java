package demo.mvc.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * @author jingxin
 * @date 2022-07-16
 */
@RestController
public class TestController {

    @PostMapping("/tt22/{id}")
    public JSONObject test(@PathVariable("id") String id, @RequestBody JSONObject body) {
        System.out.println(id);
        System.out.println(body);
        body.put("deploy", true);
        body.put("deploy2", false);
        return body;
    }

}
