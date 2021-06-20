package demo.strategy;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public class FarmDuck extends Duck{

    public FarmDuck() {
        flyAbility = FlyAbility.BAD;
        barkAbility = BarkAbility.GAGA;
    }

    @Override
    String getName() {
        return "家鸭";
    }
}
