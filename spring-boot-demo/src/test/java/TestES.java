import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.json.JSONUtil;
import org.apache.http.HttpHost;
import org.assertj.core.util.Lists;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.util.LinkedMultiValueMap;
import test.common.GameRole;
import test.es.util.EsUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-04-10
 */
//@SpringBootTest(classes = TestApplication.class)
public class TestES {

    private static RestHighLevelClient restHighLevelClient;
    private String index = "mhxy";

    private static LinkedMultiValueMap roleMartialRelMap;
    private static ArrayList<String> roleNameList;

    static {
        roleMartialRelMap = new LinkedMultiValueMap();
        roleMartialRelMap.put("剑侠客", Arrays.asList("大唐官府", "化生寺"));
        roleMartialRelMap.put("逍遥生", Arrays.asList("大唐官府", "化生寺"));
        roleMartialRelMap.put("玄彩娥", Arrays.asList("龙宫", "天宫"));
        roleMartialRelMap.put("飞燕女", Arrays.asList("大唐官府", "女儿村"));
        roleMartialRelMap.put("舞天姬", Arrays.asList("龙宫", "天宫"));
        roleMartialRelMap.put("狐美人", Arrays.asList("魔王寨", "盘丝岭"));
        roleMartialRelMap.put("骨精灵", Arrays.asList("魔王寨", "盘丝岭", "阴曹地府"));
        roleMartialRelMap.put("巨魔王", Arrays.asList("狮驼岭", "阴曹地府", "魔王寨"));
        roleMartialRelMap.put("虎头怪", Arrays.asList("狮驼岭", "魔王寨", "阴曹地府"));
        roleMartialRelMap.put("龙太子", Arrays.asList("龙宫", "天宫"));
        roleMartialRelMap.put("神天兵", Arrays.asList("龙宫", "天宫"));
        roleNameList = new ArrayList(roleMartialRelMap.keySet());
    }

    @BeforeAll
    static void beforeAll() {
        restHighLevelClient = new RestHighLevelClient(RestClient.builder(new HttpHost("192.168.2.176", 9200)));
        EsUtil.setEsClient(restHighLevelClient);
    }

    @AfterAll
    static void afterAll() throws IOException {
        restHighLevelClient.close();
    }

    @Test
    @DisplayName("创建索引")
    void testCreateIndex() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message");
                {
                    builder.field("type", "text");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        System.out.println(JSONUtil.toJsonPrettyStr(EsUtil.createIndex(index)));
    }

    @Test
    @DisplayName("查询索引是否存在")
    void testIndexExists() throws IOException {
        System.out.println(EsUtil.isIndexExists(index));
    }

    @Test
    @DisplayName("删除索引")
    void testDeleteIndex() {
        System.out.println(EsUtil.deleteIndex(index));
    }

    @Test
    @DisplayName("修改文档mapping")
    void testPutMapping() throws IOException {
        XContentBuilder builder = XContentFactory.jsonBuilder();
        builder.startObject();
        {
            builder.startObject("properties");
            {
                builder.startObject("message2");
                {
                    builder.field("type", "keyword");
                }
                builder.endObject();
            }
            builder.endObject();
        }
        builder.endObject();
        EsUtil.putMapping(index, builder);
    }

    @Test
    @DisplayName("创建文档")
    void testCreateDoc() throws IOException {
        GameRole gameRole = generateGameRole();
        IndexResponse response = EsUtil.createDoc(index, gameRole.getId(), gameRole);
        System.out.println(JSONUtil.toJsonPrettyStr(response));
    }

    private List<String> randomHobbies(int count) {
        ArrayList<String> hobbySource = Lists.newArrayList("打宝图", "挖宝图", "跑商", "运镖", "抓鬼", "杀副本", "杀星", "跑环", "摆摊", "PK", "钓鱼", "聊天");
        if (count >= hobbySource.size()) {
            return hobbySource;
        }
        List list = new ArrayList(count);
        while (list.size() != count) {
            String hobby = hobbySource.get(RandomUtil.randomInt(0, hobbySource.size()));
            if (!list.contains(hobby)) {
                list.add(hobby);
            }
        }
        return list;
    }

    private GameRole generateGameRole() {
        return generateGameRole(true);
    }

    private List<GameRole> generateBatchGameRole(int count) {
        List<GameRole> gameRoles = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            GameRole gameRole = generateGameRole(true);
            gameRoles.add(gameRole);
        }
        return gameRoles;
    }

    private GameRole generateGameRole(boolean withId) {
        GameRole gameRole = new GameRole();
        if (withId) {
            gameRole.setId(IdUtil.getSnowflake(1, 1).nextIdStr());
        }
        gameRole.setLevel(RandomUtil.randomInt(0, 70));
        gameRole.setName(roleNameList.get(RandomUtil.randomInt(0, roleMartialRelMap.keySet().size())));
        gameRole.setMartialArt(randomMartialArt(gameRole.getName()));
        gameRole.setHobbies(randomHobbies(2));
        return gameRole;
    }

    private String randomMartialArt(String name) {
        List<String> list = roleMartialRelMap.get(name);
        return list.get(RandomUtil.randomInt(0, list.size()));
    }

    @Test
    @DisplayName("查询文档")
    void testGetDoc() throws IOException {
        System.out.println(EsUtil.getDoc(index, "1381178572407967744"));
        System.out.println(EsUtil.checkDocExistence(index, "13811785724079677442"));
    }

    @Test
    @DisplayName("根据id编辑文档")
    void testUpdateDoc() throws IOException {
        GameRole gameRole = generateGameRole(false);
        gameRole.setId("1013");
        System.out.println(EsUtil.updateDoc(index, "1013", gameRole));
    }

    @Test
    @DisplayName("删除文档")
    void testDeleteDoc() throws IOException {
        System.out.println(EsUtil.deleteDoc(index, "1381178572407967744"));
        System.out.println(EsUtil.checkDocExistence(index, "1381178572407967744"));
    }

    @Test
    @DisplayName("复制索引")
    void testReindex() throws IOException {
        System.out.println(EsUtil.reindex("user2", index));
    }

    @Test
    @DisplayName("批量操作")
    void testBulk() throws IOException {
        List<GameRole> gameRoles = generateBatchGameRole(1000);
        BulkRequest bulkRequest = new BulkRequest();
        for (GameRole gameRole : gameRoles) {
            IndexRequest request = new IndexRequest(index).id(gameRole.getId()).source(JSONUtil.parseObj(gameRole));
            bulkRequest.add(request);
        }
        System.out.println(EsUtil.bulk(bulkRequest));
    }

    @Test
    @DisplayName("搜索")
    void testSearch() throws IOException {
        GameRole gameRole = new GameRole();
        gameRole.setId("1013");
        SearchRequest searchRequest = new SearchRequest(index);
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("hobbies", "杀星"))
        .filter(QueryBuilders.rangeQuery("level").gte(1).lte(2)));
        searchRequest.source(searchSourceBuilder);
        System.out.println(EsUtil.searchByConditions(searchRequest));
    }

}
