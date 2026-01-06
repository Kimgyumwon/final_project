package com.cafe.erp.store.contract;

import java.time.LocalDate;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ContractScheduler {
	
	private final ContractDAO contractDAO;
	
	public ContractScheduler(ContractDAO contractDAO) {
		this.contractDAO = contractDAO;
	}
	
	@Scheduled(cron = "0 0 0 * * *", zone = "Asia/Seoul")
	@Transactional
	public void updateStatus() {
		LocalDate today = LocalDate.now();
		LocalDate yesterday = today.minusDays(1);
		
		int activatedCount = contractDAO.updateStatusToActive(today);
		int expiredCount = contractDAO.updateStatusToExpired(yesterday);
		
		System.out.println("====== [일일 계약 상태 업데이트 결과] ======");
        System.out.println("기준일: " + today);
        System.out.println("ACTIVE 처리: " + activatedCount + "건");
        System.out.println("EXPIRED 처리: " + expiredCount + "건");
        System.out.println("==========================================");
	}

}
