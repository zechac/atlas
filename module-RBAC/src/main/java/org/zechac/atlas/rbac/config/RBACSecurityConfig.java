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
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.zechac.atlas.rbac.security.*;

public class RBACSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MAccessDecisionManager mAccessDecisionManager;
    @Autowired
    private FilterInvocationSecurityMetadataSource  metadataSource;

    @Bean
    public MFilterSecurityInterceptor mFilterSecurityInterceptor(){
        MFilterSecurityInterceptor mFilterSecurityInterceptor= new MFilterSecurityInterceptor();
        mFilterSecurityInterceptor.setAccessDecisionManager(mAccessDecisionManager);
        mFilterSecurityInterceptor.setSecurityMetadataSource(metadataSource);
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

    @Bean
    public PostAuthenticationEntryPoint authenticationEntryPoint(){
        PostAuthenticationEntryPoint postAuthenticationEntryPoint=new PostAuthenticationEntryPoint("/login");
        return postAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAt(mFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
        httpSecurity.csrf().disable().headers().frameOptions().sameOrigin();
        httpSecurity.exceptionHandling().defaultAuthenticationEntryPointFor(new PostAuthenticationEntryPoint("/login"),new AntPathRequestMatcher("/**"));
    }
}