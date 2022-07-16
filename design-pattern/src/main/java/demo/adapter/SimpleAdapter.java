package demo.adapter;

/**
 * @Author J.Star
 * @Date 2022-03-02
 */
public class SimpleAdapter implements Adapter {
    @Override
    public boolean supports(Handler handler) {
        return handler instanceof SimpleHandler;
    }

    @Override
    public void handle(Handler handler) {
        if (handler instanceof SimpleHandler) {
            SimpleHandler simpleAdapter = (SimpleHandler) handler;
            simpleAdapter.handle();
        }
    }
}
