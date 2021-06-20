package demo.responsibility;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Client {

    public static void main(String[] args) {
        InStockRequest inStockRequest = new InStockRequest();
        inStockRequest.setState("01");
        new Allocate().invokeInStock(inStockRequest);
    }
}
