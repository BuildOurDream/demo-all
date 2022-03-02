package demo.adapter;

/**
 * @Author J.Star
 * @Date 2022-03-02
 */
public class HardAdapter implements Adapter {
    @Override
    public boolean supports(Handler handler) {
        return handler instanceof HardHandler;
    }

    @Override
    public void handle(Handler handler) {
        if (handler instanceof HardHandler) {
            HardHandler hardHandler = (HardHandler) handler;
            hardHandler.handle();
        }
    }
}
