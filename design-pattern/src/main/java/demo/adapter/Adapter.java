package demo.adapter;

/**
 * @Author J.Star
 * @Date 2022-03-02
 */
public interface Adapter {

    boolean supports(Handler handler);

    void handle(Handler handler);


}
