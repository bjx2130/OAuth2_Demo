package com.example.oauth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
//                .antMatchers("/", "/home").permitAll()//不需要认证就可以访问
                .antMatchers("/**").hasAnyRole("USER")//需要身份验证
                .and().csrf().disable()
            .formLogin().successForwardUrl("/");
    }

    @Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		// 在spring5.0之后，springsecurity存储密码的格式发生了改变
		// 官方文档
		// https://docs.spring.io/spring-security/site/docs/5.0.7.BUILD-SNAPSHOT/reference/htmlsingle/
		auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder()).withUser("admin")
				.password(new BCryptPasswordEncoder().encode("111")).roles("USER").and().withUser("admin2")
				.password(new BCryptPasswordEncoder().encode("222")).roles("ADMIN");
	}
}
