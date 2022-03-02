package consumer.feign.fallback;

import common.domain.Resp;
import consumer.feign.ProviderFeignService;
import org.springframework.stereotype.Service;

/**
 * @Author J.Star
 * @Date 2022-02-17
 */
@Service
public class ProviderFallback implements ProviderFeignService {
    public Resp api1() {
        return Resp.fail("降级了");
    }
}
