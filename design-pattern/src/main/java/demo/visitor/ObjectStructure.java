package demo.visitor;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>提供高层接口,用来允许访问者访问元素</p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class ObjectStructure {

    /**
     * 维护一个集合
     */
    private List<Person> personList = new LinkedList<>();

    /**
     * 添加
     */
    public void attach(Person person) {
        personList.add(person);
    }

    /**
     * 移除
     * @param person
     */
    public void remove(Person person) {
        personList.remove(person);
    }

    public void display(Action action) {
        personList.forEach(p->p.accept(action));
    }

}
