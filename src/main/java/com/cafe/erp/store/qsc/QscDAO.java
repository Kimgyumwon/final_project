package com.cafe.erp.store.qsc;

import com.cafe.erp.store.qsc.dto.QscDTO;
import com.cafe.erp.store.qsc.dto.QscQuestionDTO;
import com.cafe.erp.store.qsc.dto.QscQuestionSearchDTO;
import com.cafe.erp.store.qsc.dto.QscSearchDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QscDAO {
    public List<QscQuestionDTO> questionList(QscQuestionSearchDTO searchDTO) throws Exception;

    public Long questionCount(QscQuestionSearchDTO searchDTO) throws Exception;

    public int addQuestion(QscQuestionDTO questionDTO) throws Exception;

    public List<QscQuestionDTO> excelQuestionList(QscQuestionSearchDTO searchDTO) throws Exception;

    public Long qscCount(QscSearchDTO searchDTO) throws Exception;

    public List<QscDTO> qscList(QscSearchDTO searchDTO) throws Exception;
}
