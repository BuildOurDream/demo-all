package demo.visitor;

/**
 * <p>声明visit操作</p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public abstract class Person {

    protected String name;

    public Person(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    abstract void accept(Action action);
}
