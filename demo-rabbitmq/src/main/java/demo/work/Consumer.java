package demo.work;

import com.rabbitmq.client.Channel;
import demo.util.ConnectionUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-07-12
 */
public class Consumer {

    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException {
        Channel channel = ConnectionUtil.getChannel();
        channel.basicQos(2);
        channel.basicConsume(QUEUE_NAME, false,
                (consumerTag, message)->{
                    try {
                        TimeUnit.SECONDS.sleep(3);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    String s = new String(message.getBody());
                    if (s.equals("")) {
                        channel.basicNack(message.getEnvelope().getDeliveryTag(), false, true);
                    } else {
                        System.out.println(s);
                        channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
                    }
                }, consumerTag->{
            System.out.println("取消");
        });
    }
}
