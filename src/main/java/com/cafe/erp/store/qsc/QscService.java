package com.cafe.erp.store.qsc;

import com.cafe.erp.store.qsc.dto.QscDTO;
import com.cafe.erp.store.qsc.dto.QscQuestionDTO;
import com.cafe.erp.store.qsc.dto.QscQuestionSearchDTO;
import com.cafe.erp.store.qsc.dto.QscSearchDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QscService {

    @Autowired
    private QscDAO qscDAO;

    public List<QscQuestionDTO> questionList(QscQuestionSearchDTO searchDTO) throws Exception {
        Long totalCount = qscDAO.questionCount(searchDTO);
        searchDTO.pageing(totalCount);
        return qscDAO.questionList(searchDTO);
    }

    public int addQuestion(QscQuestionDTO questionDTO) throws Exception {
        return qscDAO.addQuestion(questionDTO);
    }

    public List<QscQuestionDTO> excelQuestionList(QscQuestionSearchDTO searchDTO) throws Exception {
        return qscDAO.excelQuestionList(searchDTO);
    }

    public List<QscDTO> qscList(QscSearchDTO searchDTO) throws Exception {
        Long totalCount = qscDAO.qscCount(searchDTO);
        searchDTO.pageing(totalCount);
        return qscDAO.qscList(searchDTO);
    }

}
