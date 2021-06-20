package demo.iterator;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class InfoCollege implements College{

    List<Department> departments;

    public InfoCollege() {
        this.departments = new ArrayList<>();
        addDepartment("信息安全","信息安全");
        addDepartment("网络安全","网络安全");
    }

    @Override
    public String getName() {
        return "信息工程学院";
    }

    @Override
    public void addDepartment(String name, String desc) {
        Department department = new Department(name, desc);
        departments.add(department);
    }

    @Override
    public Iterator createIterator() {
        return new InfoCollegeIterator(departments);
    }
}
