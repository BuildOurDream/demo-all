package demo.boot.controller;

import demo.boot.config.RabbitMqConfiguration;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

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

    /**
     * 通过死信队列实现延时
     *
     * @param message
     * @param ttl
     */
    @GetMapping("/test2")
    public void test2(String message, String ttl) {
        rabbitTemplate.convertAndSend("EA", "RB", message, msg -> {
            msg.getMessageProperties().setExpiration(ttl);
            return msg;
        });
    }

    /**
     * 基于插件的延时队列 更加灵活
     *
     * @param message
     * @param delayTime
     */
    @GetMapping("/test3")
    public void test3(String message, Integer delayTime) {
        rabbitTemplate.convertAndSend("delayedMessageExchange", "RD", message, msg -> {
            msg.getMessageProperties().setDelay(delayTime);
            return msg;
        });
    }

    int i = 0;
    /**
     * 测试确认
     *
     * @param message
     */
    @GetMapping("/test4")
    public void test4(String message) {
//        rabbitTemplate.convertAndSend(RabbitMqConfiguration.CONFIRM_EXCHANGE_NAME, "CR", message);
        rabbitTemplate.convertAndSend(RabbitMqConfiguration.CONFIRM_EXCHANGE_NAME, "CR2", message, msg->{
            if (new String(msg.getBody(), StandardCharsets.UTF_8).equals("first")) {
                msg.getMessageProperties().setPriority(10);
            }
            return msg;
        });
    }
}
