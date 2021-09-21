package demo.authcenter.service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

/**
 * <p>用户业务</p>
 *
 * @Author J.Star
 * @Date 2021-09-20
 */
@Service
public class UserService {

    Counter counter;

    public UserService(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("login.count");
    }

    public void login() {
        counter.increment();
    }
}
