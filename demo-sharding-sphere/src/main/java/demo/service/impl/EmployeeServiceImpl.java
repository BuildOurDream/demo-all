package demo.service.impl;

import demo.entity.EmployeeEntity;
import demo.mapper.EmployeeMapper;
import demo.service.EmployeeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author J.Star
 * @since 2021-07-30
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, EmployeeEntity> implements EmployeeService {

}
