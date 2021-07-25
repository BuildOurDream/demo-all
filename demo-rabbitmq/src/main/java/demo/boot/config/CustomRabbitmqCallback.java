package demo.boot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>交换机确认与消息回退配置</p>
 *
 * @Author J.Star
 * @Date 2021-07-25
 */
@Slf4j
@Component
public class CustomRabbitmqCallback implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback, InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void afterPropertiesSet() throws Exception {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }

    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            log.info("成功发送消息");
        } else {
            log.warn("消息发送失败,原因:{}", cause);
        }
    }

    /**
     * 可以在消息传递过程中无法到达目的地时将消息返回给生产者
     *
     * @param returned
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        log.warn("消息{}被交换机{}退回,路由:{},退库原因:{}", new String(returned.getMessage().getBody()), returned.getExchange(), returned.getRoutingKey(), returned.getReplyText());
    }
}
