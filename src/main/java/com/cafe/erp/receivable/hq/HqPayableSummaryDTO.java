package com.cafe.erp.receivable.hq;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class HqPayableSummaryDTO {
	
	
    private Integer vendorCode;

    private String vendorName;

    private String baseMonth;

    private Long totalUnpaidAmount;
	
}
