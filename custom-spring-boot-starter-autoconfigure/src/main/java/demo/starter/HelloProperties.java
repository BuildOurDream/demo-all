package demo.starter;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author jingxin
 * @date 2022-07-24
 */
@Data
@ConfigurationProperties(prefix = "hello")
public class HelloProperties {

    private String prefix;

    private String suffix;
}
