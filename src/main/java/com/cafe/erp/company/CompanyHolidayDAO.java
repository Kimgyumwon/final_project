package com.cafe.erp.company;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CompanyHolidayDAO {

	public void insertHoliday(CompanyHolidayDTO companyHolidayDTO) throws Exception;
	
	public List<CompanyHolidayDTO> selectHolidayList()throws Exception;
	
	public int existsHoliday(java.sql.Date date) throws Exception;


}
