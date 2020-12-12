package com.app.common;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dlwng
 *  lucy 필터는 form data 전송 방식엔 유효하지만, @RequestBody로 전달되는 JSON 요청은 처리해주지 않는다
 *  RequestWrapper 추가하여 RequestBody로 전달되는 파라미터에도 적용
 *
 */
@Slf4j
@WebFilter(urlPatterns= "/*", filterName = "XSSFilter")
public class XSSFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		RequestWrapper requestWrapper = null;

		try {
			requestWrapper = new RequestWrapper(request);
		} catch (Exception e) {
			e.printStackTrace();
		}

		chain.doFilter(requestWrapper, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {}

	@Override
	public void destroy() {}

}