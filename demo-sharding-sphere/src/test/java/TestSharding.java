import demo.ShardingSphereApplication;
import demo.entity.Course;
import demo.mapper.CourseMapper;
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
    private CourseMapper courseMapper;

    @Test
    public void insert() {
        Course course = new Course();
        course.setCname("aaa");
        course.setUserId(11L);
        course.setStatus("ooo");
        courseMapper.insert(course);
        courseMapper.insert(course);
        courseMapper.insert(course);
        courseMapper.insert(course);
    }

    @Test
    public void query() {
        courseMapper.selectById(708825542157139968L);
        courseMapper.selectById(708825770994171905L);
    }

}
