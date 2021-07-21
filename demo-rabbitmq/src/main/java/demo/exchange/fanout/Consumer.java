package demo.exchange.fanout;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import demo.util.ConnectionUtil;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * <p>消费者</p>
 *
 * @Author J.Star
 * @Date 2021-07-18
 */
public class Consumer {

    private static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT.getType(), true);
        //声明一个队列 临时队列 名称是随机的 当消费者与队列断开连接的时候 队列就删除
        String queue = channel.queueDeclare().getQueue();
        //绑定交换机与队列
        channel.queueBind(queue, EXCHANGE_NAME, "a");
        channel.basicQos(1);
        System.out.println("等待接受a消息...");

        //接受消息回调
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("接受到消息:" + new String(message.getBody(), StandardCharsets.UTF_8));
            channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
        };
        //取消消息回调
        CancelCallback cancelCallback = (consumerTag) -> {
            System.out.println("取消消息: " + consumerTag);
        };
        //接受消息
        channel.basicConsume(queue, false, deliverCallback, cancelCallback);
    }
}
