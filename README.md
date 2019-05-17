# OAuth2_Demo
OAuth2_Demo示例

引用博文：https://blog.csdn.net/neosmith/article/details/52539927#

### 认证中心：oauth2
### 资源服务：待续



package org.resource.config;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.RemoteTokenServices;
 
@Configuration
@EnableResourceServer
public class ResServerConfig extends ResourceServerConfigurerAdapter{
	
	@Autowired
	private RemoteTokenServices tokenServices;
	
	@Primary
	@Bean
	public RemoteTokenServices tokenService() {
	    RemoteTokenServices tokenService = new RemoteTokenServices();
	    tokenService.setCheckTokenEndpointUrl(
	      "http://localhost:8888/oauth/check_token");
	    tokenService.setClientId("client");
	    tokenService.setClientSecret("secret");
	    return tokenService;
	}
	
	
	
 
	@Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.tokenServices(tokenServices);
    }
 
    @Override
    public void configure(HttpSecurity http) throws Exception {	    	
    	/*
    	 注意：
    	 1、必须先加上： .requestMatchers().antMatchers(...)，表示对资源进行保护，也就是说，在访问前要进行OAuth认证。
    	 2、接着：访问受保护的资源时，要具有哪里权限。
    	 ------------------------------------
    	 否则，请求只是被Security的拦截器拦截，请求根本到不了OAuth2的拦截器。
    	 同时，还要注意先配置：security.oauth2.resource.filter-order=3，否则通过access_token取不到用户信息。
    	 ------------------------------------
    	 requestMatchers()部分说明：
    	 Invoking requestMatchers() will not override previous invocations of ::
    	 mvcMatcher(String)}, requestMatchers(), antMatcher(String), regexMatcher(String), and requestMatcher(RequestMatcher).
    	 */
        
        http
        	// Since we want the protected resources to be accessible in the UI as well we need 
			// session creation to be allowed (it's disabled by default in 2.0.6)
        	//另外，如果不设置，那么在通过浏览器访问被保护的任何资源时，每次是不同的SessionID，并且将每次请求的历史都记录在OAuth2Authentication的details的中
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
			.and()
       		.requestMatchers()
           .antMatchers("/user","/res/**")
           .and()
           .authorizeRequests()
           .antMatchers("/user","/res/**")
           .authenticated();
    }
}




package org.resource.web;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 作为OAuth2的资源服务时，不能在Controller(或者RestController)注解上写上URL，因为这样不会被识别，会报404错误。<br>
 * <br>
 * { <br>
 * "timestamp": 1544580859138, <br>
 * "status": 404, <br>
 * "error": "Not Found", <br>
 * "message": "No message available", <br>
 * "path": "/res/getMsg" <br>
 * } <br>
 *
 *
 */
@RestController() // 作为资源服务时，不能带上url，@RestController("/res")是错的，无法识别。只能在方法上注解全路径
public class ResController {

	@RequestMapping("/res/getMsg")
	public String getMsg(String msg, Principal principal) {// principal中封装了客户端（用户，也就是clientDetails，区别于Security的UserDetails，其实clientDetails中也封装了UserDetails），不是必须的参数，除非你想得到用户信息，才加上principal。
		return "Get the msg: " + msg;
	}
}



package org.resource.web;
 
import java.security.Principal;
 
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
 
@RestController
public class UserController {
 
	@RequestMapping("/user")
	public Principal user(Principal principal) {
		//principal在经过security拦截后，是org.springframework.security.authentication.UsernamePasswordAuthenticationToken
		//在经OAuth2拦截后，是OAuth2Authentication
	    return principal;
	}
	
}



package org.resource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Hello world!
 *
 */
@SpringBootApplication
public class App 
{
    public static void main( String[] args )
    {
    	SpringApplication.run(App.class, args);
    }
}




server:
  port: 9999

security:
  oauth2:
    resource:
        filter-order: 3


