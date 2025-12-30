package com.cafe.erp.receivable;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceivableService {
	
	@Autowired
	private ReceivableDAO dao;
	
	
	public List<ReceivableSummaryDTO> receivableSearchList(
			ReceivableSearchDTO receivableSearchDTO
			){
		
		return dao.receivableSearchList(receivableSearchDTO);
		
	}
	
	
	
	
	
}
