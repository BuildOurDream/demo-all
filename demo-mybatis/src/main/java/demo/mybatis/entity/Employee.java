package demo.mybatis.entity;

import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2022-02-11
 */
@Data
@TableName("employee_1")
public class Employee {

    @TableId("id")
    private Long id;
    private String name;
    private Integer age;
    private Pet pet;

    public static Employee random() {
        Employee employee = new Employee();
        employee.setId(RandomUtil.randomLong());
        employee.setName(RandomUtil.randomString(5));
        employee.setAge(RandomUtil.randomInt());
        employee.setPet(Pet.random());
        return employee;
    }
}
