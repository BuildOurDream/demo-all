package demo.visitor;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class Client {

    public static void main(String[] args) {
        Man man = new Man("张国荣");
        Woman woman = new Woman("陈慧琳");
        ObjectStructure objectStructure = new ObjectStructure();
        objectStructure.attach(man);
        objectStructure.attach(woman);
        objectStructure.display(new Fail());

        man.accept(new Success());
    }
}
