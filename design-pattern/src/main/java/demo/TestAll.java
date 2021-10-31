package demo;

import demo.mediator.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-29
 */
public class TestAll {

    @Test
    @DisplayName("中介者模式")
    void testMediator() {
        ConcreteMediator mediator = new ConcreteMediator();
        Alarm alarm = new Alarm(mediator, "alarm");
        Curtain curtain = new Curtain(mediator, "curtain");
        TV tv = new TV(mediator, "tv");
        CoffeeMachine coffeeMachine = new CoffeeMachine(mediator, "coffeeMachine");
        alarm.sendAlarm(0);
        System.out.println();
        alarm.sendAlarm(1);
    }
}
