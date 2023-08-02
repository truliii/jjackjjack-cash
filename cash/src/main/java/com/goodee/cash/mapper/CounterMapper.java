package com.goodee.cash.mapper;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CounterMapper {
	int selectCurrCounter();
	
	int insertCounter();
	
	int updateCounter();
}
