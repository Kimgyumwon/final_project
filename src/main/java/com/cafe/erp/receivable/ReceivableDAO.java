package com.cafe.erp.receivable;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ReceivableDAO {

	// 조회된 리스트
	public List<ReceivableSummaryDTO> receivableSearchList(
			ReceivableSearchDTO receivableSearchDTO
			);
	// 페이지 네이션 총 페이지 수
	public Long receivableSearchCount(
			ReceivableSearchDTO receivableSearchDTO
			);
	
	
}
