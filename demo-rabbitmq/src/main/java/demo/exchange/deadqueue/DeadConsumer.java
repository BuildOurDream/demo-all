package demo.exchange.deadqueue;

import com.rabbitmq.client.Channel;
import demo.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <p>死信队列消费</p>
 *
 * @Author J.Star
 * @Date 2021-07-19
 */
public class DeadConsumer {

    private static final String DEAD_QUEUE = "deadQ";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        channel.basicConsume(DEAD_QUEUE,
                (d,m)->{
                    System.out.println("接收到消息:"+new String(m.getBody(), StandardCharsets.UTF_8));
                    channel.basicAck(m.getEnvelope().getDeliveryTag(), false);
                },
                d->{
                    System.out.println("取消消息");
                });
    }
}
