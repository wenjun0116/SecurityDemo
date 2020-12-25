package com.qiufeng.security.filter;

import com.alibaba.fastjson.JSON;
import com.qiufeng.security.domain.JwtUser;
import com.qiufeng.security.domain.User;
import com.qiufeng.security.utils.JwtUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import sun.plugin.liveconnect.SecurityContextHelper;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpUtils;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证用户名密码正确后，生成一个token，并将token返回客户端
 * 该类继承自UsernamePasswordAuthenticationFilter，并重写其中两个方法
 * attemptAuthentication：接收并解析用户凭证
 * successfulAuthentication：用户登录成功后，这个方法会被调用，
 * 我们在这个方法里生成token，并返回
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        //从输入流中得到登录的信息
        try {
            User user = new ObjectMapper().readValue(request.getInputStream(),User.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUserName(),user.getPassword())
            );
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("权限认证失败====================================》");
            return null;
        }
    }

    /**
     * 验证成功后调用该方法，生成token并返回
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User user = (org.springframework.security.core.userdetails.User) authResult.getPrincipal();
        JwtUser jwtUser = new JwtUser(user);

        String role = "";
        Collection<? extends GrantedAuthority> authorities = jwtUser.getAuthorities();
        for (GrantedAuthority authority: authorities) {
            role = authority.getAuthority();
        }

        String token = JwtUtils.createToken(jwtUser.getUsername(),role);
        Map<String,String> map = new HashMap<>();
        map.put(JwtUtils.TOKEN_HEADER,JwtUtils.TOKEN_PREFIX+token);
        response.getWriter().print(JSON.toJSON(map));
        //返回创建成功的token
        //但是这里创建的token只是单纯的token
        //按照jwt的规定，最后请求的时候应该是`Bearer token`
//        response.setCharacterEncoding("UTF-8");
//        response.setContentType("application/json;charset=utf-8");
//        String tokenStr = JwtUtils.TOKEN_PREFIX + token;
//        response.setHeader("Access-Control-Expose-Headers","Authorization");
//        response.setHeader(JwtUtils.TOKEN_HEADER,tokenStr);
//        SecurityContextHolder.getContext().setAuthentication(authResult);
//        RememberMeServices rememberMeServices = getRememberMeServices();
//        rememberMeServices.loginSuccess(request,response,authResult);
//        AuthenticationSuccessHandler successHandler = getSuccessHandler();
//        successHandler.onAuthenticationSuccess(request,response,authResult);
    }

    /**
     * 验证失败给出提示
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.getWriter().write("authentication failed，reason：" + failed.getMessage());
    }
}
