package com.goodee.cash.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
public class CounterService {
	public int getTodayCounter() {
		return 0;
	}
	
	public int addCounter() {
		return 0;
	}
	
	public int modifyCounter() {
		return 0;
	}
}
