package com.goodee.cash.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.cash.vo.Member;

@Mapper
public interface MemberMapper {
	Member selectMemberById(Member member);
	
	int selectIdCnt(String memberId);
	
	int insertMember(Member member);
}
