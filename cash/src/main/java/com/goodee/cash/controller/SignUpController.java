package com.goodee.cash.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class SignUpController {
	@GetMapping("/off/signUp")
	public String signUp() {
		return "/off/signUp";
	}
	@PostMapping("/off/signUpAction")
	public String signUpAction() {
		return "/off/signUp";
	}
}
