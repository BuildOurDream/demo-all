package consumer.feign;

import common.domain.Resp;
import consumer.feign.fallback.ProviderFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author J.Star
 * @Date 2022-02-17
 */
@FeignClient(name = "provider-service", path = "/provider", fallback = ProviderFallback.class)
public interface ProviderFeignService {

    @GetMapping("/api1")
    Resp api1();
}
