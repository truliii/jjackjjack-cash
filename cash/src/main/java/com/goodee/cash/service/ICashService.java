package com.goodee.cash.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.goodee.cash.vo.Cashbook;
import com.goodee.cash.vo.Hashtag;

public interface ICashService {
	public Map<String, Object> getCalendar(String memberId, Integer targetYear, Integer targetMonth);
	
	public List<Cashbook> getDailyCashbook(Map<String, Object> paramMap);
	
	public int addCashbook(Cashbook cashbook);
	
	public int modifyCashbook(Cashbook cashbook);
	
	public int removeCashbook(Cashbook cashbook);
	
	public List<Map<String, Object>> getHashtagCnt(HashMap<String, Object> paramMap);
	
	public int addHashtag(Hashtag hashtag);
	
	public int removeHashtag(Hashtag hashtag);
}
