package com.cafe.erp.stock;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StockInoutDTO {
	private Integer inputId;
	private String inputType;
	private Integer warehouseId;
	private String hqOrderId;
	private String storeOrderId;
}
