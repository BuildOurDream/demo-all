package demo.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 消息幂等性问题,重复消费解决: 一般使用全局ID(自定义生成或者使用MQ的id),每次消费时判断该ID是否消费过,可利用redis setNX的原子性
 *
 * @Author J.Star
 * @Date 2021-07-21
 */
@Configuration
public class RabbitMqConfiguration {

    private static final String DEAD_LETTER_EXCHANGE = "ED";
    private static final String DEAD_LETTER_QUEUE = "QD";

    @Bean
    public DirectExchange exchangeA() {
        return new DirectExchange("EA");
    }

    /**
     * 死信交换机
     *
     * @return
     */
    @Bean
    public DirectExchange exchangeD() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    @Bean
    public Queue queueA() {
        return QueueBuilder.durable("QA")
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("RD")
                .ttl(5000)
                .build();
    }

    @Bean
    public Queue queueB() {
        return QueueBuilder
                .durable("QB")
                .deadLetterExchange(DEAD_LETTER_EXCHANGE)
                .deadLetterRoutingKey("RD")
                .ttl(10000)
                .build();
    }

    /**
     * 死信队列
     *
     * @return
     */
    @Bean
    public Queue queueD() {
        return QueueBuilder.durable(DEAD_LETTER_QUEUE).build();
    }

    @Bean
    public Binding queueABindExchangeA(Queue queueA, DirectExchange exchangeA) {
        return BindingBuilder.bind(queueA).to(exchangeA).with("RA");
    }

    @Bean
    public Binding queueBBindExchangeA(Queue queueB, DirectExchange exchangeA) {
        return BindingBuilder.bind(queueB).to(exchangeA).with("RB");
    }

    @Bean
    public Binding queueDBindExchangeD(Queue queueD, DirectExchange exchangeD) {
        return BindingBuilder.bind(queueD).to(exchangeD).with("RD");
    }

    /**
     * 声明基于插件的延迟交换机
     *
     * @return
     */
    @Bean
    public CustomExchange delayedMessageExchange() {
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-delayed-type", "direct");
        return new CustomExchange("delayedMessageExchange", "x-delayed-message", true, true, arguments);
    }

    @Bean
    public Queue delayedQueue() {
        return QueueBuilder.durable("delayedQueue").build();
    }

    @Bean
    public Binding delayedQueueBindToDelayedExchange(Queue delayedQueue, CustomExchange delayedMessageExchange) {
        return BindingBuilder.bind(delayedQueue).to(delayedMessageExchange).with("RD").noargs();
    }

    /**
     * 确认交换机
     *
     * @return
     */
    @Bean
    public DirectExchange confirmExchange() {

        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE_NAME)
                .durable(true)
                .alternate(BACKUP_EXCHANGE_NAME)
                .build();
    }

    @Bean
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean
    public Binding confirmQueueBind2Exchange(Queue confirmQueue, DirectExchange confirmExchange) {
        return BindingBuilder.bind(confirmQueue).to(confirmExchange).with("CR");
    }

    public static final String CONFIRM_EXCHANGE_NAME = "confirmExchange";
    public static final String CONFIRM_QUEUE = "confirmQueue";
    public static final String BACKUP_EXCHANGE_NAME = "backupExchange";
    public static final String BACKUP_QUEUE = "backupQueue";
    public static final String WARNING_QUEUE = "warningQueue";
    public static final String PRIORITY_QUEUE = "priorityQueue";

    /**
     * 备份交换机
     *
     * @return
     */
    @Bean
    public FanoutExchange backupExchange() {
        return new FanoutExchange(BACKUP_EXCHANGE_NAME);
    }

    /**
     * 备份队列
     *
     * @return
     */
    @Bean
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    /**
     * 告警队列
     *
     * @return
     */
    @Bean
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean
    public Binding backupQueueBind2BackupExchange(Queue backupQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(backupQueue).to(backupExchange);
    }

    @Bean
    public Binding warningQueueBind2BackupExchange(Queue warningQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(warningQueue).to(backupExchange);
    }

    @Bean
    public Queue priorityQueue() {
        return QueueBuilder
                .durable(PRIORITY_QUEUE)
                //最大优先级
                .maxPriority(10)
                .build();
    }

    @Bean
    public Binding priorityQueueBind2BackupExchange(Queue priorityQueue, FanoutExchange backupExchange) {
        return BindingBuilder.bind(priorityQueue).to(backupExchange);
    }
}
