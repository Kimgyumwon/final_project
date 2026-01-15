package com.cafe.erp.receivable.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.cafe.erp.order.event.OrderReceivedEvent;
import com.cafe.erp.receivable.ReceivableService;

@Component
public class OrderReceivedEventListener {
	

    @Autowired
    private ReceivableService receivableService;
	
    @TransactionalEventListener(
            phase = TransactionPhase.AFTER_COMMIT
    )
    public void handleOrderReceived(OrderReceivedEvent event) {

        if ("HQ".equals(event.getOrderType())) {
            receivableService.createReceivableForHqOrder(event.getOrderNo());
        }
        
        if ("STORE".equals(event.getOrderType())) {
            receivableService.createReceivableForStoreOrder(event.getOrderNo());
        }
    }
	
}
