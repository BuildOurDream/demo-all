package consumer.config;

import com.netflix.loadbalancer.IRule;
import consumer.rule.MyLoadBalanceRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author J.Star
 * @Date 2022-02-17
 */
@Configuration
public class LoadBalanceConfig {

    @Bean
    public IRule iRule() {
        return new MyLoadBalanceRule();
    }
}
