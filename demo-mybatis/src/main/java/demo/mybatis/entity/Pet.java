package demo.mybatis.entity;

import cn.hutool.core.util.RandomUtil;
import lombok.Data;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2022-02-11
 */
@Data
public class Pet {

    private String name;

    private String color;

    public static Pet random() {
        Pet pet = new Pet();
        pet.setName(RandomUtil.randomString(5));
        pet.setColor("blue");
        return pet;
    }
}
