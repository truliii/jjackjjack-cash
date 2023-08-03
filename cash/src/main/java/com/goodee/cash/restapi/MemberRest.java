package com.goodee.cash.restapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.goodee.cash.service.MemberService;

@CrossOrigin //cross brower의 제한을 풀어주는 open rest api를 만들기 위한 애노테이션
@RestController
public class MemberRest {
	@Autowired MemberService memberService;
	
	@PostMapping("/off/rest/idCk")
	public int idCk(@RequestParam(name="memberId") String memberId) {
		int idCkRow = memberService.idCk(memberId);
		return idCkRow;
	}
}
