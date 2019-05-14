package com.example.oauth.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HelloController {

    @RequestMapping("/")
    public String index(HttpServletRequest request) {
    	request.setAttribute("remoteUser", "bjx");
        return "index";
    }

    @RequestMapping("/hello")
    public String hello(HttpServletRequest request) {
    	request.setAttribute("remoteUser", "zj");
        return "hello";
    }


}
