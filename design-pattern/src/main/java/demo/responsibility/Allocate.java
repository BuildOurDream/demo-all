package demo.responsibility;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Allocate extends AbstractInStock {

    public Allocate() {
        setNext(new Pick());
    }

    @Override
    public void process(InStockRequest inStockRequest) {
        System.out.println("配货");
        inStockRequest.setState("02");
    }

    @Override
    protected boolean couldProcess(InStockRequest inStockRequest) {
        return inStockRequest.getState().equals("01");
    }
}
