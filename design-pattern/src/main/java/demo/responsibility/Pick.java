package demo.responsibility;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Pick extends AbstractInStock {

    public Pick() {
        setNext(new Review());
    }

    @Override
    public void process(InStockRequest inStockRequest) {
        System.out.println("拣货");
        inStockRequest.setState("03");
    }

    @Override
    protected boolean couldProcess(InStockRequest inStockRequest) {
        return inStockRequest.getState().equals("02");
    }
}
