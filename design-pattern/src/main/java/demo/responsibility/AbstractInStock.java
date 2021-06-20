package demo.responsibility;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public abstract class AbstractInStock implements InStock {

    protected InStock next;

    @Override
    public abstract void process(InStockRequest inStockRequest);

    @Override
    public void setNext(InStock next) {
        this.next = next;
    }

    @Override
    public void invokeInStock(InStockRequest inStockRequest) {
        if (couldProcess(inStockRequest)) {
            this.process(inStockRequest);
        }
        if (next != null) {
            next.invokeInStock(inStockRequest);
        } else {
            System.out.println("出库完毕");
        }
    }

    protected abstract boolean couldProcess(InStockRequest inStockRequest);
}
