package demo.work;

import com.rabbitmq.client.Channel;
import demo.util.ConnectionUtil;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * <p>生产者</p>
 *
 * @Author J.Star
 * @Date 2021-07-12
 */
public class Producer {

    public static final String QUEUE_NAME = "hello2";

    public static void main(String[] args) throws IOException, InterruptedException {
        //生成队列
        Channel channel = ConnectionUtil.getChannel();
        assert channel != null;
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        //开启发布确认
        channel.confirmSelect();
        /**
         * 线程安全且有序的hash表
         * 1.将序号与消息关联
         * 2.轻松通过序号删除条目
         * 3.支持高并发
         */
        ConcurrentSkipListMap<Long, String> confirmMap = new ConcurrentSkipListMap();

        //准备监听器 监听哪些消息成功 哪些消息失败
        channel.addConfirmListener((deliveryTag, multiple) -> {
            //消息确认成功
            System.out.println("确认消息 => " + deliveryTag);
            //删除已确认消息
            if (multiple) {
                ConcurrentNavigableMap<Long, String> concurrentNavigableMap = confirmMap.headMap(deliveryTag);
                concurrentNavigableMap.clear();
            } else {
                confirmMap.remove(deliveryTag);
            }
        }, (deliveryTag, multiple) -> {
            //消息确认失败
            System.out.println("未确认消息 => " + deliveryTag);
            String message = confirmMap.get(deliveryTag);
            System.out.println("未确认的消息: " + message);
        });
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("输入消息");
            String s = scanner.nextLine();
            channel.basicPublish("", QUEUE_NAME, null, s.getBytes());
            if (s.equals("exit")) {
                System.exit(1);
            }
            //记录要发送的消息
            confirmMap.put(channel.getNextPublishSeqNo(), s);
        }
    }
}
