package com.goodee.cash;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@WebListener
public class BootListener implements ServletContextListener {
	//ANSI코드
	static final String KMJ = "\u001B[43m";
	static final String RESET = "\u001B[0m";
	
    public BootListener() {
    }

    public void contextDestroyed(ServletContextEvent sce)  { 
    }

    public void contextInitialized(ServletContextEvent sce)  { 
    	log.debug(KMJ + "BootListner : tomcat 시작" + RESET);
    }
	
}
