package demo.strategy;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public enum FlyAbility implements FlyBehavior{

    /**
     * 不会飞
     */
    NO {
        @Override
        public void fly() {
            System.out.println("不会飞");
        }
    },
    /**
     * 一般
     */
    BAD {
        @Override
        public void fly() {
            System.out.println("飞翔技术一般");
        }
    },
    /**
     * 很会飞
     */
    GOOD {
        @Override
        public void fly() {
            System.out.println("很会飞");
        }
    }
}
