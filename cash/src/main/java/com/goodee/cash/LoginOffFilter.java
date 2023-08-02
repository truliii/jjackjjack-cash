package com.goodee.cash;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebFilter("/LoginOffFilter")
public class LoginOffFilter extends HttpFilter implements Filter {
	//ANSI코드
	final String KMJ = "\u001B[43m";
	final String RESET = "\u001B[0m";    
	  
    public LoginOffFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		log.debug(KMJ + "LoginOnFilter 서블릿 접근 전 실행" + RESET);
		HttpServletRequest req = (HttpServletRequest)request; //부모타입을 자식타입으로 형변환
		HttpServletResponse res = (HttpServletResponse)response;
		HttpSession session = req.getSession();
		
		if(session.getAttribute("loginMember") != null) {
			res.sendRedirect(req.getContextPath()+"/on/cashbook");
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
