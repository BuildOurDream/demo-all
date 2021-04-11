package demo.springsecurity.config;

import demo.springsecurity.filter.TokenAuthFilter;
import demo.springsecurity.filter.TokenLoginFilter;
import org.springframework.boot.autoconfigure.h2.H2ConsoleProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * <p></p>
 *
 * @Author J.Star
 * @Date 2021-03-21
 */
@Configuration
public class CustomSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private H2ConsoleProperties h2ConsoleProperties;

    @Resource
    private UserDetailsService userDetailsService;

    @Resource
    private DataSource dataSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        String h2Path = h2ConsoleProperties.getPath();
        h2Path = h2Path.endsWith("/") ? h2Path + "**" : h2Path + "/**";
        http.exceptionHandling().accessDeniedPage("/unAuth.html")
                //关闭csrf防护
                .and().csrf().disable();

        //自定义登录页面
        http.formLogin()
                //登录页面
//                .loginPage("/login.html")
                //登录访问路径
                .loginProcessingUrl("/")
                //登录成功跳转路径
                .defaultSuccessUrl("/index.html")
                .failureUrl("/fail")
                //设置哪些路径可以直接访问,不需要认证
//                .and().authorizeRequests().antMatchers("path").permitAll()
                .and().authorizeRequests()
                .antMatchers(h2Path).permitAll()
                //该路径只有admin角色能访问
                .antMatchers("/index").hasAnyRole("admin")
                .anyRequest().authenticated()
                .and().csrf().ignoringAntMatchers(h2Path)
                .and().headers().frameOptions().sameOrigin()
                .and().rememberMe().tokenRepository(persistentTokenRepository())
                //设置有效时长,以秒为单位
                .tokenValiditySeconds(3600).userDetailsService(userDetailsService)
                .and().addFilter(new TokenAuthFilter(authenticationManagerBean()))
                .addFilter(new TokenLoginFilter())
                //会话管理
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                //注销
                .and().logout().logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .invalidateHttpSession(true);
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    /**
     * 不进行认证的路径,可以直接访问
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
//        web.ignoring().antMatchers("/login");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 记住我
     *
     * @return
     */
    @Bean
    public PersistentTokenRepository persistentTokenRepository() {
        JdbcTokenRepositoryImpl repository = new JdbcTokenRepositoryImpl();
        repository.setDataSource(dataSource);
        return repository;
    }

    @Bean(name = BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
