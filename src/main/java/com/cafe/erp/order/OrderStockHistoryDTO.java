package com.cafe.erp.order;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderStockHistoryDTO{
	private Integer inputID;
	private Integer warehouseId;
	private Integer itemId;
	private Integer orderQty;
	
}
