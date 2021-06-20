package demo.iterator;

import cn.hutool.core.collection.CollectionUtil;

import java.util.Iterator;
import java.util.List;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-06-19
 */
public class InfoCollegeIterator implements Iterator {

    List<Department> departments;

    int index = 0;

    public InfoCollegeIterator(List<Department> departments) {
        this.departments = departments;
    }

    @Override
    public boolean hasNext() {
        return CollectionUtil.isNotEmpty(departments) && index < departments.size();
    }

    @Override
    public Object next() {
        Department department = departments.get(index);
        index ++;
        return department;
    }
}
