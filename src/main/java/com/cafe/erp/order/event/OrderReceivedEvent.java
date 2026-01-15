package com.cafe.erp.order.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderReceivedEvent {
	
	private final String orderType;
	private final String orderNo;
	
}
