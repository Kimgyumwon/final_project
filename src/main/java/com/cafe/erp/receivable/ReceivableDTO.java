package com.cafe.erp.receivable;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReceivableDTO {
	
	private String receivableId;
	private String receivableType;
	private Long receivableSupplyAmount;
	private BigDecimal receivableTaxAmount;
	private Long receivableTotalAmount;
	private LocalDate receivableDate;
	private String receivableStatus;
	private String hqOrderId;
	private String StoreOrderId;
	private String contractId;
}
