package demo.springsecurity.filter;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <p>授权</p>
 * 基于web请求拦截
 * {@link org.springframework.security.web.access.intercept.FilterSecurityInterceptor}
 * 基于方法拦截
 * {@link org.springframework.security.access.intercept.aopalliance.MethodSecurityInterceptor}
 *
 * @Author J.Star
 * @Date 2021-03-21
 */
public class TokenAuthFilter extends BasicAuthenticationFilter {

    public TokenAuthFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取当前认证成功用户权限信息
        //判断如果有权限信息,放到权限上下文中
        chain.doFilter(request, response);
    }
}
