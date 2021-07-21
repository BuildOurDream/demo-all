package demo.boot.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p></p>
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
}
