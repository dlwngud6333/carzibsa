package com.app.login.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.login.service.LoginService;
import com.app.login.vo.LoginVo;

@RestController
public class LoginController {
	
	@Autowired
    LoginService loginService;
 
    @RequestMapping("/rest")
    public String index() {
        return "index";
    }
 
    @RequestMapping("/rest/login")
    public @ResponseBody
    List<LoginVo> query() throws Exception{
        return loginService.getAll();
    }
	
}
