package com.goodee.cash.controller;

import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.service.CashService;
import com.goodee.cash.service.ICashService;
import com.goodee.cash.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CashController {
	@Autowired
	private ICashService cashService;
	//ANSI코드
	static final String KMJ = "\u001B[43m";
	static final String RESET = "\u001B[0m";    
	
	@GetMapping("/on/cashbook")
	public String calendar(HttpSession session, 
							Model model,
							@RequestParam(name = "targetYear", required = false) Integer targetYear,
							@RequestParam(name = "targetMonth", required = false) Integer targetMonth) {
		log.debug(KMJ + targetYear + "<-- CashContoller.calendar() param targetYear" + RESET);
		log.debug(KMJ + targetMonth + "<-- CashContoller.calendar() param targetMonth" + RESET);
		//세션에서 로그인 된 memberId추출
		Member loginMember = (Member)session.getAttribute("loginMember");
		String loginId = loginMember.getMemberId();
		Map<String, Object> resultMap = cashService.getCalendar(loginId, targetYear, targetMonth);
		log.debug(KMJ + resultMap + "<-- CashController.calendar() resultMap" + RESET);
		
		model.addAttribute("targetYear", resultMap.get("targetYear"));
		model.addAttribute("targetMonth", resultMap.get("targetMonth"));
		model.addAttribute("lastDate", resultMap.get("lastDate"));
		model.addAttribute("beginBlank", resultMap.get("beginBlank"));
		model.addAttribute("endBlank", resultMap.get("endBlank"));
		model.addAttribute("totalTd", resultMap.get("totalTd"));
		model.addAttribute("list", resultMap.get("list"));
		
		return "/on/cashbook";
	}
}
