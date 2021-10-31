package demo.mediator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Curtain extends HomeAppliance {

    public Curtain(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void sendAlarm(int stateChange) {
        sendMessage(stateChange);
    }

    public void up() {
        System.out.println("打开窗帘");
    }

    public void down() {
        System.out.println("关窗帘睡觉");
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.getName());
    }
}
