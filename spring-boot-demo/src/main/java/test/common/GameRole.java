package test.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-04-11
 */
@Data
@Document(indexName = "gr", shards = 3, replicas = 1)
public class GameRole {

    @Id
    private String id;

    /**
     * 角色名
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String name;

    /**
     * 等级
     */
    @Field(type = FieldType.Integer)
    private int level;

    /**
     * 门派
     */
    @Field(type = FieldType.Keyword)
    private String martialArt;

    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /**
     * 爱好
     */
    @Field
    private List<String> hobbies;
}
