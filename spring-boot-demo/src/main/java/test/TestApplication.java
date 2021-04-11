package test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import test.spel.User;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-04-10
 */
@SpringBootApplication
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Bean
    public User user() {
        User user = new User();
        user.setName("JJ");
        return user;
    }
}
