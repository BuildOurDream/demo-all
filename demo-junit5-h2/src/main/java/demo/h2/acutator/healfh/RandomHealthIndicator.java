package demo.h2.acutator.healfh;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * @author jingxin
 * @date 2022-07-24
 */
@Component
public class RandomHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        boolean random = new Random().nextBoolean();
        if (random) {
            builder.up()
                    .withDetail("msg", "一切安好");
        } else {
            builder.down()
                    .withDetail("msg", "已然挂了");
        }
    }
}
