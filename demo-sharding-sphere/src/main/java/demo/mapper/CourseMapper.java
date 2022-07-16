package demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import demo.entity.Course;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author J.Star
 * @Date 2022-03-10
 */
@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
