package com.goodee.cash.service;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goodee.cash.mapper.CashMapper;
import com.goodee.cash.vo.Cashbook;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class CashService implements ICashService{
	@Autowired
	public CashMapper cashMapper;
	
	//요청단에서 targetYear와 targetMonth가 넘어오지 않으면 null
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

		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("targetYear", targetYear);
		resultMap.put("targetMonth", targetMonth);
		resultMap.put("lastDate", lastDate);
		resultMap.put("beginBlank", beginBlank);
		resultMap.put("endBlank", endBlank);
		resultMap.put("totalTd", totalTd);
		resultMap.put("list", list);

		log.debug("CashService.getCalendar() resultMap : " + resultMap.toString());

		return resultMap;
	}
}
