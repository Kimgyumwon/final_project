package com.cafe.erp.receivable.detail;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ReceivableCollectionRequestDTO {
	
	
	private String receivableId;
	private Integer amount;
	private String memo;
	private String status;
	private Integer memberId;
}
