package com.app.login.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.login.vo.LoginVo;

@Service
public interface LoginService {
	
	public List<LoginVo> getPerson() throws Exception;
	public String getNaverLogin(HttpServletRequest request) throws Exception;
	public String getNaverProfile(HttpServletRequest request) throws Exception;

}
