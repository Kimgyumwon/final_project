package com.cafe.erp.store.voc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class VocService {
	
	@Autowired
	private VocDAO vocDAO;
	
	public List<VocDTO> list(VocSearchDTO searchDTO) throws Exception {
		Long totalCount = vocDAO.count(searchDTO);
		
		searchDTO.pageing(totalCount);
		
		return vocDAO.list(searchDTO);
	}

	public int add(VocDTO vocDTO) throws Exception {
		vocDTO.setMemberId(122002);
		return vocDAO.add(vocDTO);
	}

	public VocDTO detail(Integer vocId) throws Exception {
		return vocDAO.detail(vocId);
	}

	public List<VocProcessDTO> processList(Integer vocId) throws Exception {
		return vocDAO.processList(vocId);
	}

	public int addProcess(VocProcessDTO processDTO) throws Exception {
		processDTO.setMemberId(121001);
		return vocDAO.addProcess(processDTO);
	}

}
