package demo.responsibility;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class InStockRequest {

    private String state;

    private String desc;

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }
}
