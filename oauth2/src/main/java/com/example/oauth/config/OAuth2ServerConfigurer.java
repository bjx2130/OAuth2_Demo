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
 * 			1.请求：http://localhost:8080/oauth/authorize?client_id=client&response_type=code&redirect_uri=http://www.baidu.com
 * 			     重定向：https://www.baidu.com/?code=YICO47	
 * 			2.请求 ：http://localhost:8080/oauth/token 获取 access_token
 * 			     返回：{
 *					  "access_token": "32a1ca28-bc7a-4147-88a1-c95abcc30556", // 令牌
 *					  "token_type": "bearer",
 *					  "expires_in": 2591999,
 *					  "scope": "app"
 *					}
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
