package demo.visitor;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class Woman extends Person {
    public Woman(String name) {
        super(name);
    }

    @Override
    void accept(Action action) {
        action.getResult(this);
    }
}
