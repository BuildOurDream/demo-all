package demo.visitor;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class Success extends Action {

    @Override
    public void getResult(Person person) {
        System.out.println(person.getName() + "挑战成功");
    }
}
