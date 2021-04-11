package demo.springsecurity;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.security.crypto.bcrypt.BCrypt;

//@SpringBootTest
class SpringSecurityDemoApplicationTests {

    @Test
    @DisplayName("随便测")
    void contextLoads() {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        for (String beanDefinitionName : applicationContext.getBeanDefinitionNames()) {
            System.out.println(beanDefinitionName);
        }
        ConfigurableEnvironment environment = applicationContext.getEnvironment();
        String property = environment.getProperty("os.name");
        System.out.println(property);

        System.out.println(BCrypt.hashpw("123", BCrypt.gensalt()));
        System.out.println(BCrypt.checkpw("123", "$2a$10$uZ/deQfKWm5tNqqjA0GH1OlVJ8oaPtxanvKx1f.dQJuvX0ytadHvG"));
        System.out.println(BCrypt.checkpw("123", "$2a$10$LUuNdmoj2Y6egA6xx1/Il.I47Eyze0YB0zUbEBHerLEAHAiupL4FC"));
    }

//    @BeforeAll
    static void before() {
        System.out.println("开始了");
    }

//    @AfterAll
    static void after() {
        System.out.println("结束了");
    }


}
