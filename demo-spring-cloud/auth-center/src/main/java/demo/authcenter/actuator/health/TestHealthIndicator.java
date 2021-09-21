package demo.authcenter.actuator.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 自定义健康检查
 *
 * @Author J.Star
 * @Date 2021-09-20
 */
@Component
public class TestHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws Exception {
        if (false) {
            builder.up();
        } else {
            builder.unknown();
        }
        builder.withDetail("test", "this is a test");
    }
}
