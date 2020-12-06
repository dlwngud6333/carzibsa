package com.app.login.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.login.service.LoginService;
import com.app.login.vo.LoginVo;

@RequestMapping("/rest")
@RestController
public class LoginController {

	@Autowired
	LoginService loginService;

	@RequestMapping("/")
	public String index() {
		return "index";
	}

    @RequestMapping("/login")
	public @ResponseBody List<LoginVo> query() throws Exception {
		return loginService.getPerson();
	}
 
	/**
	 * 네이버 인증 요청문 호출
	 */
	@RequestMapping("/loginNaver")
	public String getNaverLogin(HttpServletRequest request) throws Exception {
		return loginService.getNaverLogin(request);
	}

	 
	/**
	 * 네아로 접근 토큰 발급 후 네이버 프로필 API 호출
	 */
	@RequestMapping("/loginCB")
	public String getNaverProfile(HttpServletRequest request) throws Exception {
		return loginService.getNaverProfile(request);
	}

}