package com.goodee.cash.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.cash.mapper.MemberMapper;
import com.goodee.cash.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Transactional
@Service
public class MemberService {
	@Autowired
	public MemberMapper memberMapper;
	
	public Member login(Member member) {
		Member loginMember = memberMapper.selectMemberById(member);
		return loginMember;
	}
	
	public int idCk(String memberId) {
		int idCkRow = memberMapper.selectIdCnt(memberId);
		return idCkRow;
	}
	
	public int addMember(Member member) {
		int signUpRow = memberMapper.insertMember(member);
		return signUpRow;
	}
}
