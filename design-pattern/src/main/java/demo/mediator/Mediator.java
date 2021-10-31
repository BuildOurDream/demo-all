package demo.mediator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public abstract class Mediator {

    abstract void register(String name, HomeAppliance device);

    abstract void getMessage(int stateChange, String applianceName);

    public abstract void sendMessage();
}
