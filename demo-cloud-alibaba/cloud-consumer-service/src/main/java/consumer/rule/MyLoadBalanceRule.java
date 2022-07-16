package consumer.rule;

import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.Server;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @Author J.Star
 * @Date 2022-02-17
 */
public class MyLoadBalanceRule extends AbstractLoadBalancerRule {


    public Server choose(Object key) {
        List<Server> reachableServers = getLoadBalancer().getReachableServers();
        int index = ThreadLocalRandom.current().nextInt(reachableServers.size());
        return reachableServers.get(index);
    }

    public void initWithNiwsConfig(IClientConfig clientConfig) {
    }
}
