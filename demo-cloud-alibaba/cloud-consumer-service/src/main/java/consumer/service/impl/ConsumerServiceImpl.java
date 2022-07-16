package consumer.service.impl;

import common.domain.Resp;
import consumer.feign.ProviderFeignService;
import consumer.service.ConsumerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author J.Star
 * @Date 2022-02-17
 */
@Service
public class ConsumerServiceImpl implements ConsumerService {

    @Autowired
    private ProviderFeignService providerFeignService;

    public Resp consume() {
        return providerFeignService.api1();
    }
}
