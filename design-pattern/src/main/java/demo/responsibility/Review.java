package demo.responsibility;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Review extends AbstractInStock {

    public Review() {
        setNext(null);
    }

    @Override
    public void process(InStockRequest inStockRequest) {
        System.out.println("复核");
        inStockRequest.setState("04");
    }

    @Override
    protected boolean couldProcess(InStockRequest inStockRequest) {
        return "03".equals(inStockRequest.getState());
    }
}
