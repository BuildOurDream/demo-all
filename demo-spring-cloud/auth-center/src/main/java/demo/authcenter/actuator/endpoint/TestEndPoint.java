package demo.authcenter.actuator.endpoint;

import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.boot.actuate.endpoint.annotation.WriteOperation;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-09-20
 */
@Component
@Endpoint(id = "test")
public class TestEndPoint {

    @ReadOperation
    public Map getDockerInfo() {
        return Collections.singletonMap("docker info", "docker is running");
    }

    @WriteOperation
    public void stopDocker() {
        System.out.println("docker stop success");







    }
}
