package demo.starter.config;

import demo.starter.HelloProperties;
import demo.starter.service.HelloService;
import demo.starter.service.impl.HelloServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author jingxin
 * @date 2022-07-24
 */
@Configuration
@EnableConfigurationProperties(HelloProperties.class)
public class HelloAutoConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = HelloService.class)
    public HelloService helloService(HelloProperties helloProperties){
        return new HelloServiceImpl(helloProperties);
    }
}
