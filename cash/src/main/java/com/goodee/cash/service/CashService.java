package com.goodee.cash.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.mapper.CashMapper;
import com.goodee.cash.mapper.HashtagMapper;
import com.goodee.cash.vo.Cashbook;
import com.goodee.cash.vo.Hashtag;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CashService implements ICashService{
	@Autowired public CashMapper cashMapper;
	@Autowired public HashtagMapper hashtagMapper;
	//ANSI코드
	static final String KMJ = "\u001B[43m";
	static final String RESET = "\u001B[0m";    
	
	//월별 가계부 출력
	@Override
	public Map<String, Object> getCalendar(String memberId, Integer targetYear, Integer targetMonth){
		
		Calendar firstDay = Calendar.getInstance();
		firstDay.set(Calendar.DATE, 1);
		
		//원하는 년/월이 요청 매개값으로 넘어 왔다면
		if(targetYear != null && targetMonth != null) {
			firstDay.set(Calendar.YEAR, targetYear);
			//API에서 12월 -> 다음해 1월, -1월 -> 지난해 12월
			firstDay.set(Calendar.MONTH, targetMonth); 
		}
		
		targetYear = firstDay.get(Calendar.YEAR);
		targetMonth = firstDay.get(Calendar.MONTH);
		
		//마지막날짜
		int lastDate = firstDay.getActualMaximum(Calendar.DATE);
		
		//1일의 요일에서 시작 공백수 세팅 : 요일 인덱스 - 1
		int beginBlank = firstDay.get(Calendar.DAY_OF_WEEK) - 1;
		
		//마지막날짜 이후 공백 수
		int endBlank = 0;
		if((beginBlank + lastDate + endBlank) % 7 != 0) {
			endBlank = 7 - ((beginBlank + lastDate) % 7);
		}
		
		int totalTd = (beginBlank + lastDate + endBlank);
		
		//가계부 출력
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", memberId);
		paramMap.put("targetYear", targetYear);
		paramMap.put("targetMonth", targetMonth+1);
		List<Cashbook> list = cashMapper.selectCashbookListByMonth(paramMap);
		List<Map<String, Object>> htList = hashtagMapper.selectTagCountByMonth(paramMap);
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("targetYear", targetYear);
		resultMap.put("targetMonth", targetMonth);
		resultMap.put("lastDate", lastDate);
		resultMap.put("beginBlank", beginBlank);
		resultMap.put("endBlank", endBlank);
		resultMap.put("totalTd", totalTd);
		resultMap.put("list", list);
		resultMap.put("htList", htList);

		log.debug(KMJ + resultMap.toString() + "<-- CashService.getCalendar() resultMap" + RESET);

		return resultMap;
	}
	
	@Override
	public List<Cashbook> getDailyCashbook(Map<String, Object> paramMap){
		List<Cashbook> list = cashMapper.selectCashbookListByDate(paramMap);
		return list;
	}
	
	//가계부 입력
	public int addCashbook(Cashbook cashbook) {
		int addCashRow = cashMapper.insertCashbook(cashbook);
		return addCashRow;
	}
	
	//가계부 수정
	public int modifyCashbook(Cashbook cashbook) {
		
		int modifyCashRow = cashMapper.updateCashbook(cashbook);
		
		return modifyCashRow;
	}
	
	//가계부 삭제
	public int removeCashbook(Cashbook cashbook) {
		
		int removeCashRow = cashMapper.deleteCashbook(cashbook);
		
		return removeCashRow;
	}
	
	//월별 각 태그의 수
	public List<Map<String, Object>> getHashtagCnt(HashMap<String, Object> paramMap){
		List<Map<String, Object>> list = hashtagMapper.selectTagCountByMonth(paramMap);
		log.debug(KMJ + list.toString() + "<-- HashtagService.getHashtag list" + RESET);
		return list;
	}
	
	//태그 입력
	public int addHashtag(Hashtag hashtag) {
		int addHashtagRow = hashtagMapper.insertHashtag(hashtag);
		return addHashtagRow;
	}
	
	//태그 삭제
	public int removeHashtag(Hashtag hashtag) {
		int removeHashtagRow = hashtagMapper.deleteHashtag(hashtag);
		return removeHashtagRow;
	}
}
