package com.cafe.erp.member.approval;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MemberApprovalDAO {
	int insertApprovalAuto(MemberApprovalDTO dto) throws Exception;
}
