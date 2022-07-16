package consumer.interceport;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author J.Star
 * @Date 2022-02-19
 */
@Component
public class MyFeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate template) {
        template.header("requestId", String.valueOf(ThreadLocalRandom.current().nextInt(9999)));
    }
}
