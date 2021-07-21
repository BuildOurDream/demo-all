package demo.boot.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-07-21
 */
@RestController
public class TestController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/test")
    public void test(String message) {
        rabbitTemplate.convertAndSend("EA", "RA", message);
        rabbitTemplate.convertAndSend("EA", "RB", message);
    }
}
