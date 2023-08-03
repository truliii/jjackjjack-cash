package com.goodee.cash.controller;

import java.net.http.HttpRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.goodee.cash.mapper.CashMapper;
import com.goodee.cash.service.CashService;
import com.goodee.cash.service.ICashService;
import com.goodee.cash.vo.Cashbook;
import com.goodee.cash.vo.Member;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class CashController {
	@Autowired
	private ICashService cashService;
	//ANSI코드
	static final String KMJ = "\u001B[43m";
	static final String RESET = "\u001B[0m";    
	
	//가계부 달력
	@GetMapping("/on/cashbook")
	public String cashbookCalendar(HttpSession session, 
							Model model,
							@RequestParam(name = "targetYear", required = false) Integer targetYear,
							@RequestParam(name = "targetMonth", required = false) Integer targetMonth) {
		log.debug(KMJ + targetYear + "<-- CashContoller.calendar() param targetYear" + RESET);
		log.debug(KMJ + targetMonth + "<-- CashContoller.calendar() param targetMonth" + RESET);
		//세션에서 로그인 된 memberId추출
		Member loginMember = (Member)session.getAttribute("loginMember");
		String loginId = loginMember.getMemberId();
		log.debug(KMJ + loginId + " <--CashController.calendar loginId" + RESET);
		Map<String, Object> resultMap = cashService.getCalendar(loginId, targetYear, targetMonth);
		log.debug(KMJ + resultMap + "<-- CashController.calendar() resultMap" + RESET);
		
		model.addAttribute("targetYear", resultMap.get("targetYear"));
		model.addAttribute("targetMonth", resultMap.get("targetMonth"));
		model.addAttribute("lastDate", resultMap.get("lastDate"));
		model.addAttribute("beginBlank", resultMap.get("beginBlank"));
		model.addAttribute("endBlank", resultMap.get("endBlank"));
		model.addAttribute("totalTd", resultMap.get("totalTd"));
		model.addAttribute("list", resultMap.get("list"));
		model.addAttribute("htList", resultMap.get("htList"));
		
		return "/on/cashbook";
	}
	
	//일별 가계부 리스트
	@GetMapping("/on/cashbookListByDate")
	public String cashbookListByDate(HttpSession session,
									Model model,
									@RequestParam(name="targetYear") Integer targetYear,
									@RequestParam(name="targetMonth") Integer targetMonth,
									@RequestParam(name="targetDate") Integer targetDate) {
		
		Member loginMember = (Member)session.getAttribute("loginMember");
		String loginId = loginMember.getMemberId();
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("memberId", loginId);
		paramMap.put("targetYear", targetYear);
		paramMap.put("targetMonth", targetMonth + 1);
		paramMap.put("targetDate", targetDate);
		List<Cashbook> list = cashService.getDailyCashbook(paramMap);
		log.debug(KMJ + list + "<-- CashController.cashbookListByDate list" + RESET);
		
		String targetMonthStr = "";
		String targetDateStr = "";
		if(targetMonth<8) {
			targetMonthStr = "0"+(targetMonth + 1);
		}
		if(targetDate<9) {
			targetDateStr = "0"+targetDate;
		}
		
		String targetDay = targetYear + "-" + targetMonthStr + "-" + targetDateStr;
		
		model.addAttribute("targetDay", targetDay);
		model.addAttribute("targetYear", targetYear);
		model.addAttribute("targetMonth", targetMonth);
		model.addAttribute("targetDate", targetDate);
		model.addAttribute("list", list);
		return "/on/cashbookListByDate";
	}
	
	//가계부 입력액션
	@PostMapping("/on/addCashbook")
	public String addCashbook(HttpSession session,
								Model model,
								@RequestParam(name="targetYear") Integer targetYear,
								@RequestParam(name="targetMonth") Integer targetMonth,
								@RequestParam(name="targetDate") Integer targetDate,
								@RequestParam(name="addCategory") String category,
								@RequestParam(name="addCashbookDate") String cashbookDate,
								@RequestParam(name="addMemo") String memo,
								@RequestParam(name="addPrice") Integer price) {
		Member loginMember = (Member)session.getAttribute("loginMember");
		String loginId = loginMember.getMemberId();
		Cashbook cashbook = new Cashbook();
		cashbook.setCategory(category);
		cashbook.setMemberId(loginId);
		cashbook.setCashbookDate(cashbookDate);
		cashbook.setMemo(memo);
		cashbook.setPrice(price);
		
		
		int addCashRow = cashService.addCashbook(cashbook);
		log.debug(KMJ + addCashRow + "<--CashController.addCashbook addCashRow" + RESET);
		
		return "redirect:/on/cashbookListByDate?targetYear="+targetYear+"&targetMonth="+targetMonth+"&targetDate="+targetDate;
	}
	
	//가계부 수정액션
	@PostMapping("/on/modifyCashbook")
	public String modifyCashbook(@RequestParam(name="targetYear") Integer targetYear,
								 @RequestParam(name="targetMonth") Integer targetMonth,
								 @RequestParam(name="targetDate") Integer targetDate,
								 @RequestParam(name="cashbookNo") Integer cashbookNo,
								 @RequestParam(name="category") String category,
								 @RequestParam(name="price") Integer price,
								 @RequestParam(name="memo") String memo) {
		
		Cashbook cashbook = new Cashbook();
		cashbook.setCashbookNo(cashbookNo);
		cashbook.setCategory(category);
		cashbook.setPrice(price);
		cashbook.setMemo(memo);
		int modifyCashRow = cashService.modifyCashbook(cashbook);
		log.debug(KMJ + modifyCashRow + "<-- CashController.removeCashbook removeCashRow" + RESET);
		
		return "redirect:/on/cashbookListByDate?targetYear?="+targetYear+"&targetMonth"+targetMonth+"&targetDate="+targetDate;
	}
	
	//가계부 삭제액션
	@GetMapping("/on/removeCashbook")
	public String removeCashbook(@RequestParam(name="targetYear") Integer targetYear,
								 @RequestParam(name="targetMonth") Integer targetMonth,
								 @RequestParam(name="targetDate") Integer targetDate,
								 @RequestParam(name="cashbookNo") Integer cashbookNo) {
		Cashbook cashbook = new Cashbook();
		cashbook.setCashbookNo(cashbookNo);
		
		int removeCashRow = cashService.removeCashbook(cashbook);
		log.debug(KMJ + removeCashRow + "<-- CashController.removeCashbook removeCashRow" + RESET);
		
		return "redirect:/on/cashbookListByDate?targetYear?="+targetYear+"&targetMonth"+targetMonth+"&targetDate="+targetDate;
	}
}
