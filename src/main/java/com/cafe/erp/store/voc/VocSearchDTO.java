package com.cafe.erp.store.voc;

import java.time.LocalDate;

import com.cafe.erp.util.Pager;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class VocSearchDTO extends Pager {
	
	private String vocType;
	private Integer vocStatus;
	private LocalDate searchStartDate;
	private LocalDate searchEndDate;
	private String storeName;
	private String vocTitle;

}
