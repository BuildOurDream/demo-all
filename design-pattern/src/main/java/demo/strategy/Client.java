package demo.strategy;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class Client {

    public static void main(String[] args) {
        FarmDuck farmDuck = new FarmDuck();
        farmDuck.display();
        WildDuck wildDuck = new WildDuck();
        wildDuck.display();
        farmDuck.setFlyAbility(FlyAbility.GOOD);

        wildDuck.setBarkAbility(BarkAbility.NO);
        farmDuck.display();
        wildDuck.display();
    }
}
