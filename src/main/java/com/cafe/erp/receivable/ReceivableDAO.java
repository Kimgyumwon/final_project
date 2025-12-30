package com.cafe.erp.receivable;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReceivableDAO {

	
	public List<ReceivableSummaryDTO> receivableSearchList(
			ReceivableSearchDTO receivableSearchDTO
			);
	
	
}
