package com.cafe.erp.receivable.detail;

import java.util.Date;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReceivableOrderSummaryDTO {
	
	private String receivableId;
	private Date orderDate;
	private Integer itemCount;
	private Integer supplyAmount;
	private Integer taxAmount;
	private Integer totalAmount;
}
