package test.spel;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-04-08
 */
@Data
public class User {

    private String id;

    private String name;

    private int age;

    /**
     * 门派
     */
    private String country;

    private String language;

    private String timeZone;

    private List<String> hobbies = Arrays.asList("aaa", "bbb", "ccc", "ddd", "eee");

    private List<Obj> objs;

    public void fillObjs(int size) {
        List list = new ArrayList();
        for (int i = 0; i < size; i++) {
            Obj obj = new Obj();
            obj.setO1("o1_" + (i + 1));
            obj.setO2(i + 1);
            list.add(obj);
        }
        this.objs = list;
    }
}
