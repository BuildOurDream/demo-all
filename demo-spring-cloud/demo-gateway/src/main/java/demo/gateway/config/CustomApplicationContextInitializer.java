package demo.gateway.config;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-21
 */
public class CustomApplicationContextInitializer  implements ApplicationContextInitializer {
    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        System.out.println("应用初始化器执行...");
    }
}
