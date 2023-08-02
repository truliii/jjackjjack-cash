package com.goodee.cash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goodee.cash.mapper.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class LoginService {
	@Autowired
	public MemberMapper loginMapper;
	
	public int getMemberId(String memberId) {
		int row = 0;
		
		row = loginMapper.selectMemberId();
		
		return row;
	}
}
