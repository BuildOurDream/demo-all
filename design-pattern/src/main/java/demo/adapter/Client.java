package demo.adapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author J.Star
 * @Date 2022-03-02
 */
public class Client {

    List<Adapter> adapters;

    public Client() {
        adapters = new ArrayList<>();
        adapters.add(new SimpleAdapter());
        adapters.add(new HardAdapter());
    }

    void doHandle(Handler handler) {
        for (Adapter adapter : adapters) {
            if (adapter.supports(handler)) {
                adapter.handle(handler);
            }
        }
    }


    public static void main(String[] args) {
        Client client = new Client();
        client.doHandle(new SimpleHandler());
        client.doHandle(new HardHandler());
    }
}
