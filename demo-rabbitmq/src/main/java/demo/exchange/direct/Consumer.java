package demo.exchange.direct;

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

    private static final String EXCHANGE_NAME = "direct";
    private static final String LOG_LEVEL_INFO = "info";
    private static final String LOG_LEVEL_ERROR = "error";

    public static void main(String[] args) throws IOException {
//        String level = LOG_LEVEL_INFO;
        String level = LOG_LEVEL_ERROR;
        Channel channel = ConnectionUtil.getChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT.getType(), true);
        //声明一个队列 临时队列 名称是随机的 当消费者与队列断开连接的时候 队列就删除
        channel.queueDeclare(level, true, false, false, null);
        //绑定交换机与队列
        channel.queueBind(level, EXCHANGE_NAME, level);
        channel.basicQos(1);
        System.out.println("等待接受" + level + "消息...");

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
        channel.basicConsume(level, false, deliverCallback, cancelCallback);
    }
}
