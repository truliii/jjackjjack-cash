package com.goodee.cash.controller;

import java.net.http.HttpRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.service.MemberService;
import com.goodee.cash.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class MemberController {
	@Autowired private MemberService memberService;
	//ANSI코드
	static final String KMJ = "\u001B[43m";
	static final String RESET = "\u001B[0m";    
	
	//회원가입 페이지로 이동
	@GetMapping("/off/signUp")
	public String signUp() {
		return "/off/signUp";
	}
	
	//회원가입 액션
	@PostMapping("/off/signUp")
	public String signUp(@RequestParam(name="memberId", defaultValue = "") String memberId,
						 @RequestParam(name="memberPw", defaultValue = "") String memberPw) {
		if(memberId.equals("") || memberPw.equals("")) {
			return "/off/signUp";
		}
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		int signUpRow = memberService.addMember(member);
		log.debug(KMJ + signUpRow + "<--MemberController.signUp signUpRow" + RESET);
		return "redirect:/off/login";
	}
	
	//로그인 페이지로 이동
	@GetMapping("/off/login")
	public String login(HttpServletRequest request) {
		//아이디 저장 기능
		Cookie[] cookies = request.getCookies();
		//cookies에 값이 있고 loginId키로 저장된 값이 있는 경우에는 request속성객체에 담는다
		if(cookies != null) {
			for(Cookie c : cookies) {
				if(c.getName().equals("loginId")) {
					//Cookie에 저장된 아이디가 있으면 request속성에 담아 view로 forward
					request.setAttribute("loginId", c.getValue());
				}
			}
		}
		
		return "/off/login";
	}
	
	//로그인 액션
	@PostMapping("/off/login")
	public String login(HttpServletRequest request,
						HttpServletResponse response,
						@RequestParam(name = "memberId") String memberId,
						@RequestParam(name = "memberPw") String memberPw,
						@RequestParam(name = "idCookie", defaultValue = "" ) String idCookie) {
		Member member = new Member();
		member.setMemberId(memberId);
		member.setMemberPw(memberPw);
		Member loginMember = memberService.login(member);
		
		//로그인 실패 : loginMember가 null이면 로그인 실패
		if(loginMember == null) {
			log.debug(KMJ + "로그인 실패" + RESET);
			return "redirect:/off/login";
		}
		
		//로그인 성공 : session에 로그인 정보 저장
		//만약 idCookie값이 null이 아니라면 (아이디 저장 체크한 경우) Cookie에 해당 id저장
		log.debug(KMJ + loginMember.toString() + "로그인 성공" + RESET);
		if(!idCookie.equals("")) {
			Cookie cookie = new Cookie("loginId", loginMember.getMemberId());
			cookie.setMaxAge(60*60*1); //cookie유효시간 1시간
			response.addCookie(cookie); //response에 쿠키 추가
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", loginMember );
		
		return "redirect:/on/cashbook";
	}
	
	//로그아웃 액션
	@GetMapping("/on/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/on/login";
	}
}
