package util;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class EncodingFilter implements Filter{
	@Override
	public void destroy() {
		System.out.println("필터 소멸");
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		req.setCharacterEncoding("utf-8");
		System.out.println("EncodingFilter 실행 완료");
		chain.doFilter(req, resp);
		System.out.println("Response완료");
	}

	@Override
	public void init(FilterConfig config) throws ServletException {
		System.out.println("필터 초기화");
	}
}
