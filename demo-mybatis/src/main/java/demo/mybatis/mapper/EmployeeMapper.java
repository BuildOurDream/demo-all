package demo.mybatis.mapper;

import demo.mybatis.entity.Employee;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2022-02-12
 */
public interface EmployeeMapper {

    Employee getById(Long id);
}
