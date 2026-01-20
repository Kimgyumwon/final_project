package com.cafe.erp.member.attendance;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cafe.erp.member.approval.MemberApprovalDTO;

@Mapper
public interface MemberAttendanceDAO {
    
	public Long countAttendanceList(MemberAttendanceSearchDTO searchDTO) throws Exception;

	public List<MemberAttendanceDTO> attendanceList(MemberAttendanceSearchDTO searchDTO) throws Exception;
    
	public MemberLeaveStatsDTO selectLeaveStatsNOW(int memberId) throws Exception;

	public List<MemberAttendanceDTO> selectApprovedAttendance(int memberId) throws Exception;
    

	public int insertAttendance(MemberAttendanceDTO dto) throws Exception;
	public MemberLeaveStatsDTO selectLeaveStats(@Param("memberId") int memberId, @Param("year") int year) throws Exception;


}