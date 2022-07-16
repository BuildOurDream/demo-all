package provider.domain;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author J.Star
 * @Date 2022-02-19
 */
@Data
@Component
@ConfigurationProperties(prefix = "user")
public class UserDemo {

    private String name;

    private Integer age;
}
