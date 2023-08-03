package com.goodee.cash.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.goodee.cash.vo.Cashbook;
import com.goodee.cash.vo.Hashtag;

@Mapper
public interface CashMapper {
	List<Cashbook> selectCashbookListByMonth(Map<String, Object> paramMap);
	
	List<Cashbook> selectCashbookListByDate(Map<String, Object> paramMap);
	
	List<Cashbook> selectCashbookListByTag(Hashtag hashtag);
	
	List<Map<String, Object>> selectCashbookCountByTag();
	
	Cashbook selectCashbookByNo(Cashbook cashbook);
	
	int insertCashbook(Cashbook cashbook);
	
	int updateCashbook(Cashbook cashbook);
	
	int deleteCashbook(Cashbook cashbook);

}
