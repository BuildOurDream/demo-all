import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetMappingsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import test.TestApplication;
import test.data.es.GameRoleRepository;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-05-09
 */
@SpringBootTest(classes = TestApplication.class)
public class TestDataEs {

    @Resource
    private GameRoleRepository gameRoleRepository;

    @Resource
    private RestHighLevelClient elasticsearchClient;

    @Test
    void testCreateIndex() throws IOException {
        gameRoleRepository.save(TestES.generateGameRole());
//        System.out.println(gameRoleRepository.count());
        gameRoleRepository.findAll().forEach(System.out::println);
        System.out.println(gameRoleRepository.findByMartialArt("魔王寨", PageRequest.of(0,1)));
        GetMappingsRequest getMappingsRequest = new GetMappingsRequest();
        getMappingsRequest.indices("gr");
    }
}
