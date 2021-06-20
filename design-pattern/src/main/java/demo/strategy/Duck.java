package demo.strategy;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public abstract class Duck {

    FlyAbility flyAbility;

    BarkAbility barkAbility;

    abstract String getName();

    public void display() {
        System.out.println(getName());
        fly();
        quark();
    }

    public void setFlyAbility(FlyAbility flyAbility) {
        this.flyAbility = flyAbility;
    }

    public void setBarkAbility(BarkAbility barkAbility) {
        this.barkAbility = barkAbility;
    }

    public void fly() {
        if (flyAbility != null) {
            flyAbility.fly();
        }
    }

    public void quark() {
        if (barkAbility != null) {
            barkAbility.bark();
        }
    }
}
