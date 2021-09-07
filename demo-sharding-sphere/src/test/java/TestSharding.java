import demo.ShardingSphereApplication;
import demo.entity.EmployeeEntity;
import demo.service.EmployeeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-07-30
 */
@SpringBootTest(classes = ShardingSphereApplication.class)
public class TestSharding {

    @Autowired
    private EmployeeService employeeService;

    @Test
    public void test1() {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setName("test")
                .setAge(10);
        employeeService.save(entity);
        System.out.println(entity.getId());
    }

}
