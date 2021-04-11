package demo.springsecurity.config;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import demo.springsecurity.entity.UserEntity;
import demo.springsecurity.service.UserService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>提供用户信息</p>
 *
 * @Author J.Star
 * @Date 2021-03-21
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Resource
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userService.getOne(new LambdaQueryWrapper<UserEntity>().eq(UserEntity::getAccount, username));
        if (userEntity == null) {
            throw new UsernameNotFoundException("用户不存在");
        }
        UserDetails userDetails = User.withUsername(username)
                .password(BCrypt.hashpw(userEntity.getPassword(), BCrypt.gensalt()))
                .authorities("admin", "ROLE_admin2").build();
        return userDetails;
    }


}
