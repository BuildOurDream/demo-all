package test.common;

import lombok.Data;

import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-04-11
 */
@Data
public class GameRole {

    private String id;

    /**
     * 角色名
     */
    private String name;

    /**
     * 等级
     */
    private int level;

    /**
     * 门派
     */
    private String martialArt;

    /**
     * 爱好
     */
    private List<String> hobbies;
}
