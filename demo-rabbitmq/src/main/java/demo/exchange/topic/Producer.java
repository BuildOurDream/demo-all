package demo.exchange.topic;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import demo.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * <p>生产者</p>
 *
 * @Author J.Star
 * @Date 2021-07-18
 */
public class Producer {

    private static final String EXCHANGE_NAME = "topic";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC.getType(), true);
        channel.confirmSelect();
        Scanner scanner = new Scanner(System.in);

        ConcurrentSkipListMap<Long, String> confirmMap = new ConcurrentSkipListMap();

        //消息确认成功
        ConfirmCallback confirmCallback = (deliveryTag, multiple) -> {
            System.out.println("确认消息 => " + deliveryTag);
            //删除已确认消息
            if (multiple) {
                ConcurrentNavigableMap<Long, String> concurrentNavigableMap = confirmMap.headMap(deliveryTag);
                concurrentNavigableMap.clear();
            } else {
                confirmMap.remove(deliveryTag);
            }
        };

        //消息确认失败
        ConfirmCallback noConfirmCallback = (deliveryTag, multiple) -> {
            System.out.println("未确认消息 => " + deliveryTag);
            String message = confirmMap.get(deliveryTag);
            System.out.println("未确认的消息: " + message);
        };

        //添加消息确认监听器
        channel.addConfirmListener(confirmCallback, noConfirmCallback);

        while (true) {
            System.out.println("routingKey:");
            String routingKey = scanner.next();
            System.out.println("消息:");
            String next = scanner.next();
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, next.getBytes(StandardCharsets.UTF_8));
            System.out.printf("发送消息: %s 到路由%s\n", next, routingKey);
            confirmMap.put(channel.getNextPublishSeqNo(), next);
        }
    }
}
