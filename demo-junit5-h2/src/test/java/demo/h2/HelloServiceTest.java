package demo.h2;

import demo.starter.config.HelloAutoConfiguration;
import demo.starter.service.HelloService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * @author jingxin
 * @date 2022-07-24
 */
@SpringBootTest(classes = HelloAutoConfiguration.class)
public class HelloServiceTest {

    @Autowired
    private HelloService helloService;

    @Test
    void testHello() {
        assertNotNull(helloService);
        helloService.sayHello("Tom");
    }
}
