package com.cafe.erp.store.qsc.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QscQuestionDTO {

    private Integer listId;
    private String listCategory;
    private String listQuestion;
    private Integer listMaxScore;
    private Boolean listIsUse;

    public String getListIsUseStr() { return listIsUse ? "사용" : "미사용"; }

}
