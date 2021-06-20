package demo.strategy;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-20
 */
public enum BarkAbility implements BarkBehavior {

    NO {
        @Override
        public void bark() {
            System.out.println("不会叫");
        }
    },
    GAGA {
        @Override
        public void bark() {
            System.out.println("嘎嘎叫");
        }
    },
    GEGE {
        @Override
        public void bark() {
            System.out.println("咯咯叫");
        }
    }
}
