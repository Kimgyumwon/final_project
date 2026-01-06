package com.cafe.erp.store.voc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/store/voc/")
public class VocController {
	
	@Autowired
	private VocService vocService;
	
	@GetMapping("list")
	public String list(VocSearchDTO searchDTO, Model model) throws Exception {
		List<VocDTO> vocList = vocService.list(searchDTO);
		model.addAttribute("list", vocList);
		model.addAttribute("pager", searchDTO);
		
		return "voc/list";
	}
	
	@PostMapping("add")
	@ResponseBody
	public Map<String, Object> addVoc(@RequestBody VocDTO vocDTO) throws Exception { 
		return result(vocService.add(vocDTO)); 
	}
	
	@GetMapping("detail")
	public String detail(Integer vocId, Model model) throws Exception {
		VocDTO vocDTO = vocService.detail(vocId);
		List<VocProcessDTO> processList = vocService.processList(vocId);
		model.addAttribute("dto", vocDTO);
		model.addAttribute("list", processList);
		
		return "voc/detail";
	}
	
	@PostMapping("addProcess")
	@ResponseBody
	public Map<String, Object> addVocProcess(@RequestBody VocProcessDTO processDTO) throws Exception { 
		return result(vocService.addProcess(processDTO));
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
