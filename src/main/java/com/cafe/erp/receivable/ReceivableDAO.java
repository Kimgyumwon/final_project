package com.cafe.erp.receivable;

import java.util.List;


import org.apache.ibatis.annotations.Mapper;

import com.cafe.erp.receivable.detail.ReceivableItemDTO;
import com.cafe.erp.receivable.detail.ReceivableOrderSummaryDTO;
import com.cafe.erp.receivable.detail.ReceivableRoyaltyDTO;

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
	
	// detail page 물품 대금 요약
	public List<ReceivableOrderSummaryDTO> orderSummary(ReceivableSummaryDTO receivableSummaryDTO);
	
	// detail page 물품 대금 품목 상세 리스트
	public List<ReceivableItemDTO> receivableItemList(String receivableId);
	
	// detail page 가맹비 미수 내역
	public ReceivableRoyaltyDTO receivableRoyalty(ReceivableSummaryDTO receivableSummaryDTO);
	
	
	
}
