package demo.iterator;

import java.util.Iterator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public interface College {

    String getName();

    void addDepartment(String name, String desc);

    Iterator createIterator();
}
