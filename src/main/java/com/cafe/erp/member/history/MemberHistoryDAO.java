package com.cafe.erp.member.history;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberHistoryDAO {

	int insertLoginHistory(MemberLoginHistoryDTO historyDTO) throws Exception;
	List<MemberLoginHistoryDTO> selectLoginHistoryList(MemberHistorySearchDTO memberHistorySearchDTO)throws Exception;
	// 로그인 이력 엑셀 + 개수	
	Long totalCount(MemberHistorySearchDTO memberHistorySearchDTO)throws Exception;
	
	/*
	 * Long countLoginHistory(MemberHistorySearchDTO memberHistorySearchDTO)throws
	 * Exception;
	 */
}
