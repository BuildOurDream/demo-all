package demo.h2.acutator.metrics;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

/**
 * @author jingxin
 * @date 2022-07-24
 */
@Component
public class DemoMetrics {

    private Counter counter;

    public DemoMetrics(MeterRegistry meterRegistry) {
        counter = meterRegistry.counter("demo.invoke.count");
        invoke();
    }

    public void invoke(){
        counter.increment();
    }
}
