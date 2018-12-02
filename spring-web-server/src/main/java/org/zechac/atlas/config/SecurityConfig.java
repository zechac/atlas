package org.zechac.atlas.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.zechac.atlas.rbac.config.RBACSecurityConfig;
import org.zechac.atlas.rbac.security.LoginFilter;
import org.zechac.atlas.rbac.security.MAuthenticationFailureHandler;
import org.zechac.atlas.rbac.security.MAuthenticationSuccessHandler;
import org.zechac.atlas.rbac.security.MFilterSecurityInterceptor;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends RBACSecurityConfig {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.addFilterAt(loginFilter(), UsernamePasswordAuthenticationFilter.class);
        httpSecurity.addFilterAt(mFilterSecurityInterceptor(), FilterSecurityInterceptor.class);
        httpSecurity
            // 由于使用的是JWT，我们这里不需要csrf
            .csrf().disable()
            // 基于token，所以不需要session
            //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
            .authorizeRequests()
            //.antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
            // 允许对于网站静态资源的无授权访问
            // 对于获取token的rest api要允许匿名访问
            .antMatchers("/api/**").hasAnyRole("ROLE_User")
            .anyRequest().anonymous()
            .and()
            .formLogin().loginPage("/login").loginProcessingUrl("/login").permitAll()
            .and().logout().permitAll();
        //httpSecurity.addFilterBefore(new JwtLoginFilter(authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 禁用缓存
        httpSecurity.headers().cacheControl();
    }

}
