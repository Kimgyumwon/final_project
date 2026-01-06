package com.cafe.erp.receivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cafe.erp.receivable.detail.ReceivableItemDTO;
import com.cafe.erp.receivable.detail.ReceivableOrderSummaryDTO;
import com.cafe.erp.receivable.detail.ReceivableRoyaltyDTO;
import com.cafe.erp.util.Pager;

@Service
public class ReceivableService {
	
	@Autowired
	private ReceivableDAO dao;
	
	
	public List<ReceivableSummaryDTO> receivableSearchList(
			ReceivableSearchDTO receivableSearchDTO
			){
		Long totalCount = dao.receivableSearchCount(receivableSearchDTO);
		try {
			Pager pager = receivableSearchDTO.getPager();
			pager.pageing(totalCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dao.receivableSearchList(receivableSearchDTO);
	}
	
	public List<ReceivableOrderSummaryDTO> orderSummary(ReceivableSummaryDTO receivableSummaryDTO) {
		return dao.orderSummary(receivableSummaryDTO);
	}
	
	
	
	public List<ReceivableItemDTO > receivableItem(String receivableId) {
		return dao.receivableItemList(receivableId);
	}
	
	
	public ReceivableRoyaltyDTO receivableRoyalty(ReceivableSummaryDTO receivableSummaryDTO) {
		return dao.receivableRoyalty(receivableSummaryDTO);
	}
	
	
	
}
