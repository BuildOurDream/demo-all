package demo.exchange.deadqueue;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import demo.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>普通队列消费</p>
 *
 * @Author J.Star
 * @Date 2021-07-19
 */
public class NormalConsumer {

    private static final String NORMAL_EXCHANGE = "normalEx";
    private static final String DEAD_EXCHANGE = "deadEx";
    private static final String NORMAL_QUEUE = "normalQ";
    private static final String DEAD_QUEUE = "deadQ";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        //声明普通交换机与死信交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.TOPIC);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.TOPIC);

        Map<String, Object> arguments= new HashMap<>();
        //正常队列设置死信交换机
        arguments.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        //设置死信routingKey
        arguments.put("x-dead-letter-routing-key", "dead");
        //队列最大长度,超过该长度消息会被放入死信队列
//        arguments.put("x-max-length", 3);
        //声明普通队列与死信队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, arguments);
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);

        //绑定队列与交换机
        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "normal");
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "dead");
        System.out.println("等待接受消息...");

        channel.basicConsume(NORMAL_QUEUE,
                (d, m) -> {
                    String msg = new String(m.getBody(), StandardCharsets.UTF_8);
                    if (msg.length() > 2) {
                        System.out.println("收到消息:" + msg);
                        channel.basicAck(m.getEnvelope().getDeliveryTag(), false);
                    } else {
                        System.out.println("拒绝消息:" + msg);
                        channel.basicReject(m.getEnvelope().getDeliveryTag(), false);
                    }
                },
                d -> {
                    System.out.println("取消消息:" + d);
                });
    }
}
