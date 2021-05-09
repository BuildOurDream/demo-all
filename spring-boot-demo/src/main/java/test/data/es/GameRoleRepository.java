package test.data.es;

import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import test.common.GameRole;

import java.util.List;

/**
 * <p></p>
 *
 * 方法返回类型
 * List<T>
 * Stream<T>
 * SearchHits<T>
 * List<SearchHit<T>>
 * Stream<SearchHit<T>>
 * SearchPage<T>
 *
 * @Author J.Star
 * @Date 2021-05-06
 */
@Repository
public interface GameRoleRepository extends ElasticsearchRepository<GameRole, String> {

    /**
     * 通过门派查找
     *
     * @param martialArt
     * @return
     */
    List<GameRole> findByMartialArt(String martialArt);
    SearchHits<GameRole> findByMartialArtNot(String martialArt);

    @Query("{\"bool\": {\n" +
            "    \"must\":[\n" +
            "      {\n" +
            "        \"match\": {\n" +
            "          \"name\": \"飞燕女\"" +
            "        }\n" +
            "      }\n" +
            "      ]\n" +
            "  }}")
//    @Highlight(fields = {
//            @HighlightField(name = "name"),
//            @HighlightField(name = "level")
//    })
    List<SearchHit<GameRole>> findByMartialArt(String martialArt, Pageable pageable);

}
