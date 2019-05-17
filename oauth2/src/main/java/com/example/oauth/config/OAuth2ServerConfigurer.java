package com.example.oauth.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 
 * @author bWX535804
 * 
 * 授权码流程：
 * 			1.GET请求：http://localhost:8888/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
 * 			     	重定向：https://www.baidu.com/?code=YICO47	
 * 			2.POST请求 ：http://localhost:8888/oauth/token?code=5lMWwD&grant_type=authorization_code&redirect_uri=http://www.baidu.com&scope=app 
 * 					添加header: Authorization:Basic 编码(client:secret)
 * 											     		
 * 						返回：{
 *						    "access_token": "7989cf2d-331e-4e74-80c8-506b01cd4d5f",
 *						    "token_type": "bearer",
 *						    "expires_in": 43199,
 *						    "scope": "app"
 *						}
 *
 *			3.请求资源服务器：http://resource:port/hello?access_token=7989cf2d-331e-4e74-80c8-506b01cd4d5f
 *
 *
 */

@Configuration
@EnableAuthorizationServer
public class OAuth2ServerConfigurer extends AuthorizationServerConfigurerAdapter {
	@Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory() // 使用in-memory存储
                .withClient("client") // client_id
                .secret("secret") // client_secret
                .authorizedGrantTypes("authorization_code") // 该client允许的授权类型
                .scopes("app"); // 允许的授权范围
    }

}
