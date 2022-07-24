package demo.starter.service.impl;

import demo.starter.HelloProperties;
import demo.starter.service.HelloService;
import lombok.RequiredArgsConstructor;

/**
 * @author jingxin
 * @date 2022-07-24
 */
@RequiredArgsConstructor
public class HelloServiceImpl implements HelloService {

    private final HelloProperties helloProperties;

    @Override
    public void sayHello(String curUser) {
        System.out.println(String.format("%s:%s,%s", helloProperties.getPrefix(), curUser, helloProperties.getSuffix()));
    }
}
