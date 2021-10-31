package demo.mediator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class CoffeeMachine extends HomeAppliance {

    public CoffeeMachine(Mediator mediator, String name) {
        super(mediator, name);
        mediator.register(name, this);
    }

    public void makeCoffee() {
        System.out.println("泡一杯咖啡");
        sendMessage(1);
    }

    @Override
    public void sendMessage(int stateChange) {
        this.getMediator().getMessage(stateChange, this.getName());
    }
}
