package com.cafe.erp.store.contract;

import java.io.File;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cafe.erp.files.FileManager;

@Service
@Transactional(rollbackFor = Exception.class)
public class ContractService {
	
	@Autowired
	private ContractDAO contractDAO;
	@Autowired
	private FileManager fileManager;
	
	@Value("${erp.upload.contract}")
	private String uploadPath;
	
	@Transactional(readOnly = true)
	public List<ContractDTO> list() throws Exception {
		return contractDAO.list();
	}

	public int add(ContractDTO contractDTO, List<MultipartFile> files) throws Exception {
		
		int year = LocalDate.now().getYear();
		String prefix = "CT" + year;
		String maxId = contractDAO.maxContractId(prefix);
		
		int nextNum = 1;
		if (maxId != null) {
			String num = maxId.substring(prefix.length());
			nextNum = Integer.parseInt(num) + 1;
		}
		
		String newId = prefix + String.format("%03d", nextNum);
		contractDTO.setContractId(newId);
		
		int result = contractDAO.add(contractDTO);
		
		uploadFiles(files, newId);
		
		return result;
	}

	public ContractDTO getDetail(String contractId) {
		return contractDAO.getDetail(contractId);
	}

	public int update(ContractDTO contractDTO, List<MultipartFile> newFiles, List<Integer> deleteFileIds) throws Exception {
		int result = contractDAO.update(contractDTO);
		
		deleteFiles(deleteFileIds);
		uploadFiles(newFiles, contractDTO.getContractId());
		
		return result;
	}
	
	private void uploadFiles(List<MultipartFile> files, String contractId) throws Exception {
		if (files == null || files.isEmpty()) return; 
		
		File dir = new File(uploadPath);
		if (!dir.exists()) dir.mkdirs(); 

		for (MultipartFile f : files) {
			if (f == null || f.isEmpty()) continue;
			
			String fileName = fileManager.fileSave(dir, f);
			
			ContractFileDTO contractFileDTO = new ContractFileDTO();
			contractFileDTO.setFileSavedName(fileName);
			contractFileDTO.setFileOriginalName(f.getOriginalFilename());
			contractFileDTO.setContractId(contractId);
			
			contractDAO.addFile(contractFileDTO); 
		}
	}

	private void deleteFiles(List<Integer> deleteFileIds) throws Exception {
		if (deleteFileIds == null || deleteFileIds.isEmpty()) return;
		
		File dir = new File(uploadPath);

		for (Integer fileId : deleteFileIds) {
			ContractFileDTO fileDTO = contractDAO.selectFile(fileId); 
			
			if (fileDTO != null) {
				fileManager.fileDelete(dir, fileDTO.getFileSavedName());
				contractDAO.deleteFile(fileId);
			}
		}
	}

}