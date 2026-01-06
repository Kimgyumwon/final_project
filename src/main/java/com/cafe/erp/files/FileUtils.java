package com.cafe.erp.files;

import java.io.File;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component 
public class FileUtils {

    @Value("${erp.upload.profile}")
    private String uploadPath;

    public String uploadFile(MultipartFile file) throws Exception {
        
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String savedName = UUID.randomUUID().toString() + extension;
        
        File target = new File(uploadPath, savedName);
        
        
        if (!target.getParentFile().exists()) {
            target.getParentFile().mkdirs(); 
        }
        
        file.transferTo(target);
        
        return savedName;
    }
    
    public void deleteFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return;
        }

        File file = new File(uploadPath, fileName);
        
        if (file.exists()) {
            file.delete();
            System.out.println("파일 삭제 성공: " + fileName);
        } else {
            System.out.println("삭제할 파일 없음: " + fileName);
        }
    }
}