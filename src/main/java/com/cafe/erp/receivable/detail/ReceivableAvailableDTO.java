package com.cafe.erp.receivable.detail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReceivableAvailableDTO {
	
	private String receivableId;
	private String sourceType;
	private Integer remainAmount;
	
	
}
