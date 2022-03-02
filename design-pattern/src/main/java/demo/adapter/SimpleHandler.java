package demo.adapter;

/**
 * @Author J.Star
 * @Date 2022-03-02
 */
public class SimpleHandler implements Handler{
    @Override
    public void handle() {
        System.out.println("simple");
    }
}
