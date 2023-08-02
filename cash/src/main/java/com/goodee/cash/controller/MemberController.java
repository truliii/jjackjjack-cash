package com.goodee.cash.controller;

import java.net.http.HttpRequest;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.service.LoginService;

@Controller
public class MemberController {
	@Autowired
	private LoginService loginService;
	
	//로그인 페이지로 이동
	@GetMapping("/off/login")
	public String login() {
		return "/login";
	}
	
	//로그인 액션
	@PostMapping("/off/login")
	public String login(ServletRequest request,
						HttpSession session,
						@RequestParam(name = "id") String id,
						@RequestParam(name = "pw") String pw) {
		String msg = "";
		int row = loginService.getMemberId(id);
		if(row == 0) {
			msg = "아이디 또는 비밀번호를 확인해주세요";
		} else {
			session.setAttribute("loginId", id);
		}
		request.setAttribute("msg", msg);
		return "redirect : /login";
	}
	
	//로그아웃 액션
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.invalidate();
		return "/logout";
	}
}
