package org.zechac.atlas.rbac.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zechac.atlas.rbac.security.*;

public class RBACSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MAccessDecisionManager mAccessDecisionManager;

    @Autowired
    private MFilterInvocationSecurityMetadataSource mFilterInvocationSecurityMetadataSource;

    @Bean
    public MFilterSecurityInterceptor mFilterSecurityInterceptor(){
        MFilterSecurityInterceptor mFilterSecurityInterceptor= new MFilterSecurityInterceptor();
        mFilterSecurityInterceptor.setAccessDecisionManager(mAccessDecisionManager);
        mFilterSecurityInterceptor.setSecurityMetadataSource(mFilterInvocationSecurityMetadataSource);
        return mFilterSecurityInterceptor;
    }

    @Bean
    public LoginFilter loginFilter() throws Exception {
        LoginFilter loginFilter= new LoginFilter();
        loginFilter.setAuthenticationSuccessHandler(new MAuthenticationSuccessHandler("/index"));
        loginFilter.setAuthenticationFailureHandler(new MAuthenticationFailureHandler("/login?success=false"));
        loginFilter.setAuthenticationManager(authenticationManager());
        return loginFilter;
    }

    @Bean
    protected UserDetailsService customUserService() {
        return new SecurityUserService();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        authenticationManagerBuilder
            // 设置UserDetailsService
            .userDetailsService(customUserService())
            // 使用BCrypt进行密码的hash
            .passwordEncoder(passwordEncoder());
    }

    // 装载BCrypt密码编码器
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(customUserService());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            // 由于使用的是JWT，我们这里不需要csrf
            .csrf().disable()
            // 基于token，所以不需要session
            .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 允许对于网站静态资源的无授权访问
            // 对于获取token的rest api要允许匿名访问
            .anyRequest().anonymous().and().formLogin().loginPage("/login")
            .failureUrl("/login?error").permitAll().and().logout().permitAll();
            //httpSecurity.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
            // 禁用缓存
            httpSecurity.headers().cacheControl();
    }
}