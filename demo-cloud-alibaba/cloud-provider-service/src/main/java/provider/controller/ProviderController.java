package provider.controller;

import common.domain.Resp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import provider.domain.UserDemo;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author J.Star
 * @Date 2022-02-17
 */
@RefreshScope
@RestController
@RequestMapping("/provider")
public class ProviderController {

    @Value("${server.port}")
    private String port;

    @Value("${test.value:0}")
    private String testValue;

    @Autowired
    private UserDemo userDemo;

    @GetMapping("/api1")
    public Resp api1(@RequestHeader("requestId") String requestId) throws InterruptedException {
//        int i = 1/0;
//        TimeUnit.SECONDS.sleep(3);
        Map map = new HashMap();
        map.put("port", port);
        map.put("requestId", requestId);
        map.put("testValue", testValue);
        map.put("user", userDemo);
        return Resp.ok(map);
    }
}
