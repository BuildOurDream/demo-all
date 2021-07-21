package demo.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-07-12
 */
public class ConnectionUtil {

    public static Channel getChannel() {
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("192.168.2.176");
        connectionFactory.setUsername("admin");
        connectionFactory.setPassword("123");
        try {
            Connection connection = connectionFactory.newConnection();
            return connection.createChannel();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        return null;
    }
}
