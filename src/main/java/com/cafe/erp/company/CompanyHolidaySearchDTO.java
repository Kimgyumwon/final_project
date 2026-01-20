package com.cafe.erp.company;

import com.cafe.erp.util.Pager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @ToString
public class CompanyHolidaySearchDTO extends Pager {

  private String startDate;     
  private String endDate;       
  private String type;         
  private String keyword;   
  private Boolean isActive;  

  private Long totalCount;
}
