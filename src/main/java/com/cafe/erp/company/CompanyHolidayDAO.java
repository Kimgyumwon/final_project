package com.cafe.erp.company;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyHolidayDAO {

	public void insertHoliday(CompanyHolidayDTO companyHolidayDTO) throws Exception;
	
	public List<CompanyHolidayDTO> selectHolidayAllActive();

	  List<CompanyHolidayDTO> selectHolidayList(CompanyHolidaySearchDTO companyHolidaySearchDTO) throws Exception;
	  Long totalCount(CompanyHolidaySearchDTO companyHolidaySearchDTO) throws Exception;	

	  int updateHoliday(CompanyHolidayDTO companyHolidayDTO) throws Exception;         
	  int updateHolidayActive(CompanyHolidayDTO companyHolidayDTO) throws Exception;   

	  int existsHoliday(java.sql.Date date) throws Exception;



}
