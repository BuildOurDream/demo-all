import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import test.TestApplication;
import test.common.GameRole;
import test.data.es.GameRoleRepository;
import test.es.util.EsUtil;

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
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @Test
    void testCreateIndex() throws IOException {
        gameRoleRepository.save(TestES.generateGameRole());
        System.out.println(gameRoleRepository.count());
        gameRoleRepository.findAll().forEach(System.out::println);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.matchAllQuery()).aggregation(AggregationBuilders.sum("总等级").field("level"));
        SearchRequest searchRequest = new SearchRequest("gr");
        searchRequest.source(searchSourceBuilder);
        System.out.println(EsUtil.searchByConditions(searchRequest));
        System.out.println(elasticsearchTemplate.indexOps(GameRole.class).getMapping());
        System.out.println(elasticsearchTemplate.get("1391357289042153472", GameRole.class));
    }
}
