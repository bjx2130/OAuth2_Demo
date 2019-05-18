package com.resource.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 
 * 作为OAuth2的资源服务时，不能在Controller(或者RestController)注解上写上URL，因为这样不会被识别，会报404错误。
 * 
 * { "timestamp": 1544580859138, "status": 404, "error": "Not Found", "message":
 * "No message available", "path": "/res/getMsg" }
 */
@RestController
public class ResController {

	@RequestMapping("/res/getMsg")
	public String getMsg(String msg, Principal principal) {// principal中封装了客户端（用户，也就是clientDetails，区别于Security的UserDetails，其实clientDetails中也封装了UserDetails），不是必须的参数，除非你想得到用户信息，才加上principal。
		return "Get the msg: " + msg;
	}

}
