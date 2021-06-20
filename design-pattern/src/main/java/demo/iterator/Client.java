package demo.iterator;

import java.util.ArrayList;
import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class Client {

    public static void main(String[] args) {
        List<College> collegeList = new ArrayList<>();
        collegeList.add(new ComputerCollege());
        collegeList.add(new InfoCollege());
        OutputImpl output = new OutputImpl(collegeList);
        output.printCollege();
    }
}
