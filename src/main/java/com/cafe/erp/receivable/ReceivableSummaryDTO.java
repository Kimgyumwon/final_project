package com.cafe.erp.receivable;

import lombok.Getter;

import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReceivableSummaryDTO {
	
	// 조회 시 채권 리스트 출력
	private Long storeId;
    private String storeName;
    private String baseMonth;   
    private Long totalAmount;
    
    
    // 조회 시 금액 요약
    private long totalUnpaidAmount;
    private int receivableCount;
    private long avgUnpaidAmount;
    
    
    
}
