package demo.boot.consumer;

import com.rabbitmq.client.Channel;
import demo.boot.config.RabbitMqConfiguration;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@Component
public class DeadLetterQueueConsumer {

    @RabbitListener(queues = "QD")
    public void listen(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("QD accept message: " + msg);
    }

    @RabbitListener(queues = "QA")
    public void listen1(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("QA accept message: " + msg);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = "QB")
    public void listen2(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info("QB accept message: " + msg);
        channel.basicReject(message.getMessageProperties().getDeliveryTag(), false);
    }

    @RabbitListener(queues = RabbitMqConfiguration.CONFIRM_QUEUE)
    public void confirm(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info(RabbitMqConfiguration.CONFIRM_QUEUE + " accept message: " + msg);
    }

    /**
     * 备份队列
     *
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMqConfiguration.BACKUP_QUEUE)
    public void backup(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info(RabbitMqConfiguration.BACKUP_QUEUE + " accept message: " + msg);
    }

    /**
     * 报警队列
     *
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMqConfiguration.WARNING_QUEUE, ackMode = "MANUAL")
    public void warning(Message message, Channel channel) throws IOException {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info(RabbitMqConfiguration.WARNING_QUEUE + "收到不可路由消息: " + msg);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    /**
     * 优先级队列
     *
     * @param message
     * @param channel
     */
    @RabbitListener(queues = RabbitMqConfiguration.PRIORITY_QUEUE)
    public void priority(Message message, Channel channel) {
        String msg = new String(message.getBody(), StandardCharsets.UTF_8);
        log.info(RabbitMqConfiguration.PRIORITY_QUEUE + "收到消息: " + msg);
    }
}
