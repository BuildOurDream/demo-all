package demo.strategy;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class WildDuck extends Duck{


    public WildDuck() {
        flyAbility = FlyAbility.GOOD;
        barkAbility = BarkAbility.GEGE;
    }

    @Override
    String getName() {
        return "野鸭";
    }
}
