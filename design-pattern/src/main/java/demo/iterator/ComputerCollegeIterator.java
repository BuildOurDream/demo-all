package demo.iterator;

import java.util.Iterator;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class ComputerCollegeIterator implements Iterator {

    Department[] departments;

    /**
     * 遍历的位置
     */
    int index = 0;

    public ComputerCollegeIterator(Department[] departments) {
        this.departments = departments;
    }

    @Override
    public boolean hasNext() {
        return departments.length > index && departments[index] != null;
    }

    @Override
    public Object next() {
        Department department = departments[index];
        index++;
        return department;
    }
}
