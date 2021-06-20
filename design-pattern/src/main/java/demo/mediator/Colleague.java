package demo.mediator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public abstract class Colleague {

    private Mediator mediator;

    private String name;

    public Colleague(Mediator mediator, String name) {
        this.mediator = mediator;
        this.name = name;
    }

    public Mediator getMediator() {
        return this.mediator;
    }

    public String getName() {
        return this.name;
    }

    public abstract void sendMessage(int stateChange);
}
