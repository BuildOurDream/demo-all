package demo.iterator;

import lombok.Data;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
@Data
public class Department {

    private String name;
    private String desc;

    public Department(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }
}
