package com.app.login.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.app.login.vo.LoginVo;

@Service
public interface LoginService {
	
	public List<LoginVo> getAll() throws Exception;
	
}
