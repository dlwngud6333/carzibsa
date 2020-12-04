package com.app.login.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.app.login.vo.LoginVo;

@Repository
public interface LoginMapper {
	
	public List<LoginVo> getAll() throws Exception;
	
}
