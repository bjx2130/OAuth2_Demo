package com.example.oauth.config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;


@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfigurer extends AuthorizationServerConfigurerAdapter {
	
	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;
	
	/**
	 * 在spring5.0之后，springsecurity client密码 和 user密码必须用 PasswordEncoder 加密
	 * @return
	 */
    @Bean
    public BCryptPasswordEncoder bcryptPasswordEncoder(){
    	return new BCryptPasswordEncoder();
    }
    
    
	
	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.inMemory() // 使用in-memory存储
				.withClient("client") // client_id
				.secret(bcryptPasswordEncoder.encode("secret")) // client_secret
				.authorizedGrantTypes("authorization_code") // 该client允许的授权类型
				// .autoApprove(true) //登录后绕过批准询问(/oauth/confirm_access)
				.redirectUris("http://www.baidu.com")
				.scopes("app"); // 允许的授权范围

	}
    
    
	/**
	 * =======可选配置=========
	 * 资源服务器与认证服务器分离时
	 * 例如资源服务器 ResourceServerTokenServices 的  RemoteTokenServices 实现类时
	 * 需要授权资源服务器权限：http://localhost:8888/oauth/check_token
	 */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
		security
			.tokenKeyAccess("permitAll()")
			.checkTokenAccess("isAuthenticated()")
			.allowFormAuthenticationForClients();
		
	}

}

