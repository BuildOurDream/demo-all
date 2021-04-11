package demo.springsecurity.service.impl;

import demo.springsecurity.entity.UserEntity;
import demo.springsecurity.mapper.UserMapper;
import demo.springsecurity.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author J.Star
 * @since 2021-03-21
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, UserEntity> implements UserService {

}
