package demo.responsibility;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public interface InStock {

    void process(InStockRequest inStockRequest);

    void setNext(InStock inStock);

    void invokeInStock(InStockRequest inStockRequest);
}
