package demo.mediator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public abstract class Mediator {

    abstract void register(String name, Colleague colleague);

    abstract void getMessage(int stateChange, String colleagueName);

    public abstract void sendMessage();
}
