package com.goodee.cash;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.springframework.beans.factory.annotation.Autowired;

import com.goodee.cash.service.CounterService;

@WebListener
public class CounterListener implements HttpSessionListener {
	@Autowired
	private CounterService counterService;
	
	//ANSI코드
	final String KMJ = "\u001B[43m";
	final String RESET = "\u001B[0m";
	
    public CounterListener() {
    }

    @Override
    public void sessionCreated(HttpSessionEvent se)  { 
    	System.out.println(KMJ + se.getSession().getId() + "세션이 생성되었습니다" + RESET);
		//방문자수 count
		//세션이 만들어지면 현재방문자 수+1 -> application 속성영역에 저장
		//db방문자수+1 -> db수정
    	ServletContext application = se.getSession().getServletContext(); //session이 만들어진 servlet context 받아오기
    	int currCounter = (Integer)(application.getAttribute("currCounter"));
    	application.setAttribute("currCounter", currCounter+1);
		
		//오늘 방문자 수가 0인 경우 1로 저장, 0이 아닌 경우는 +1
		int todayCount = counterService.getTodayCounter();
		if(todayCount == 0) {
			counterService.addCounter();
		} else {
			counterService.modifyCounter();
		}
    }
    
    @Override
    public void sessionDestroyed(HttpSessionEvent se)  { 
    	System.out.println(KMJ + se.getSession().getId() + " 세션이 종료되었습니다" + RESET);
		//세션 종료하면 현재 방문자수 -1
		ServletContext application = se.getSession().getServletContext(); //session이 만들어진 servlet context 받아오기
		int currCounter = (Integer)(application.getAttribute("currCounter"));
		application.setAttribute("currCounter", currCounter-1);
    }
	
}
