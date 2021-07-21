package demo.boot;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-07-21
 */
@Component
public class Consumer {

    @RabbitListener(queues = "QD")
    public void listen(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("QD accept message: " + msg);
    }

//    @RabbitListener(queues = "QA")
    public void listen1(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("QA accept message: " + msg);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }

//    @RabbitListener(queues = "QB")
    public void listen2(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        System.out.println("QB accept message: " + msg);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }
}
