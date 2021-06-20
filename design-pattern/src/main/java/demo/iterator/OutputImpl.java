package demo.iterator;

import java.util.Iterator;
import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class OutputImpl {

    /**
     * 学院集合
     */
    List<College> colleges;

    public OutputImpl(List<College> colleges) {
        this.colleges = colleges;
    }

    /**
     * 输出学院
     */
    public void printCollege() {
        Iterator<College> iterator = colleges.iterator();
        while (iterator.hasNext()) {
            College college = iterator.next();
            System.out.println(college.getName());
            printDepartment(college.createIterator());
        }
    }

    /**
     * 输出系
     */
    public void printDepartment(Iterator iterator) {
        while (iterator.hasNext()) {
            Department department = (Department) iterator.next();
            System.out.println(department.getName());
        }
    }
}
