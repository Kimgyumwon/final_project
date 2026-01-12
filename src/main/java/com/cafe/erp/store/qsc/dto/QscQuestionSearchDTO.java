package com.cafe.erp.store.qsc.dto;

import com.cafe.erp.util.Pager;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class QscQuestionSearchDTO extends Pager {

    private String searchCategory;
    private Boolean searchIsUse;
    private String searchQuestion;

}
