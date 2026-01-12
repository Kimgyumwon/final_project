package com.cafe.erp.store.qsc;

import com.cafe.erp.store.qsc.dto.QscDTO;
import com.cafe.erp.store.qsc.dto.QscQuestionDTO;
import com.cafe.erp.store.qsc.dto.QscQuestionSearchDTO;
import com.cafe.erp.store.qsc.dto.QscSearchDTO;
import com.cafe.erp.util.ExcelUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/store/qsc/")
public class QscController {

    @Autowired
    private QscService qscService;

    @GetMapping("list")
    public String qscList(QscSearchDTO searchDTO, Model model) throws Exception {
        List<QscDTO> qscList = qscService.qscList(searchDTO);
        model.addAttribute("list", qscList);
        model.addAttribute("pager", searchDTO);

        return "qsc/list";
    }

    @GetMapping("add")
    public String qscAdd(QscQuestionSearchDTO searchDTO, Model model) throws Exception {
        searchDTO.setSearchIsUse(true);
        searchDTO.setPerPage(100L);
        List<QscQuestionDTO> qscQuestionList = qscService.questionList(searchDTO);
        model.addAttribute("list", qscQuestionList);

        return "qsc/add";
    }

    @GetMapping("admin/question")
    public String questionList(QscQuestionSearchDTO searchDTO, Authentication authentication, Model model) throws Exception {
        String memberId = authentication.getName();
        if (memberId.startsWith("2")) return "error/forbidden";

        List<QscQuestionDTO> questionList = qscService.questionList(searchDTO);
        model.addAttribute("list", questionList);
        model.addAttribute("pager", searchDTO);

        return "qsc/question_list";
    }

    @PostMapping("admin/question/add")
    @ResponseBody
    Map<String, Object> addQuestion(@RequestBody QscQuestionDTO questionDTO) throws Exception {
        return result(qscService.addQuestion(questionDTO));
    }

    @GetMapping("question/downloadExcel")
    public void downloadExcelQuestion(QscQuestionSearchDTO searchDTO, HttpServletResponse response) throws Exception {
        List<QscQuestionDTO> list = qscService.excelQuestionList(searchDTO);
        String[] headers = {"ID", "카테고리", "질문", "배점", "사용여부"};

        ExcelUtil.download(list, headers, "QSC 질문 목록", response, (row, dto) -> {
            row.createCell(0).setCellValue(dto.getListId());
            row.createCell(1).setCellValue(dto.getListCategory());
            row.createCell(2).setCellValue(dto.getListQuestion());
            row.createCell(3).setCellValue(dto.getListMaxScore());
            row.createCell(4).setCellValue(dto.getListIsUseStr());
        });
    }

    private Map<String, Object> result(int result) {
        Map<String, Object> response = new HashMap<>();

        if (result > 0) {
            response.put("message", "등록 완료");
            response.put("status", "success");
        } else {
            response.put("status", "error");
            response.put("message", "등록 실패");
        }

        return response;
    }

}
