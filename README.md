# OAuth2_Demo
OAuth2_Demo示例

引用博文：https://blog.csdn.net/neosmith/article/details/52539927#

spring security官网：https://docs.spring.io/spring-security/site/docs/5.0.7.BUILD-SNAPSHOT/reference/htmlsingle/

### 认证中心：oauth2
### 资源服务：resource
	
	
	资源服务器通过RemoteTokenServices从认证中心可以获取当前用户信息




/**
 * 
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
 
 
 
 
 
 # 基于 Spring Security OAuth2 的单点登录系统
 	https://github.com/janlle/sso-service
