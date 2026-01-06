package com.cafe.erp.files;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;

@Controller
@RequestMapping("/fileDownload/")
public class FileController {
	
	@Value("${erp.upload.contract}")
	private String contractPath;
	
	@Value("${erp.upload.profile}")
	private String profilePath;

	@GetMapping("contract")
	public ResponseEntity<Resource> contractFile(@RequestParam String fileSavedName, @RequestParam String fileOriginalName) {
		try {
			Path filePath = Paths.get(contractPath).resolve(fileSavedName).normalize();
			Resource resource = new UrlResource(filePath.toUri());
			
			if (!resource.exists()) {
				return ResponseEntity.notFound().build();
			}
			
			String encodedOriginalName = UriUtils.encode(fileOriginalName, StandardCharsets.UTF_8);
            
            String contentDisposition = "attachment; filename=\"" + encodedOriginalName + "\"";

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                    .body(resource);
		} catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
	}
	
	@GetMapping("profile")
    public ResponseEntity<Resource> Profile(@RequestParam String fileSavedName){
        try {
            Path filePath = Paths.get(profilePath).resolve(fileSavedName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            
            if (!resource.exists()) {
                return ResponseEntity.notFound().build();
            }
            
            String contentType = "image/jpeg"; 
            String lowerName = fileSavedName.toLowerCase();
            
            if(lowerName.endsWith(".png")) {
                contentType = "image/png";
            } else if(lowerName.endsWith(".gif")) {
                contentType = "image/gif";
            }
            
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileSavedName + "\"")
                    .header(HttpHeaders.CONTENT_TYPE, contentType)
                    .body(resource);
                    
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }
	
}
