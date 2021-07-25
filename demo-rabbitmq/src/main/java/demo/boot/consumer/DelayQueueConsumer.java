package demo.boot.consumer;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * <p>消费者 基于插件的延迟消息</p>
 *
 * @Author J.Star
 * @Date 2021-07-24
 */
@Component
public class DelayQueueConsumer {

    @RabbitListener(queues = "delayedQueue")
    public void consume(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("delayedQueue accept message: " + msg);
    }
}
