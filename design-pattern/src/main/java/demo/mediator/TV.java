package demo.mediator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class TV extends HomeAppliance {

    public TV(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void open() {
        System.out.println("打开电视");
    }

    public void stop() {
        System.out.println("关闭电视");
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.getName());
    }
}
