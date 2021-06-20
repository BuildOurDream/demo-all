package demo.iterator;

import java.util.Iterator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class ComputerCollege implements College{

    Department[] departments;

    int count;

    public ComputerCollege() {
        departments = new Department[5];
        addDepartment("java","java专业");
        addDepartment("php","php专业");
        addDepartment("bigdata","大数据专业");
    }

    @Override
    public String getName() {
        return "计算机学院";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department department = new Department(name, desc);
        departments[count] = department;
        count++;
    }

    @Override
    public Iterator createIterator() {
        return new ComputerCollegeIterator(departments);
    }
}
