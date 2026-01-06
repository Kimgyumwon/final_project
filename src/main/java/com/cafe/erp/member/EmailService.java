package com.cafe.erp.member;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
	
	@Autowired
    private JavaMailSender javaMailSender;

    public void sendPasswordEmail(String memEmail, String memPassword, String memName) {
        SimpleMailMessage message = new SimpleMailMessage();
        
        message.setTo(memEmail); 
        message.setSubject("신규 사원 등록 및 임시 비밀번호 안내");
        
        String text = "안녕하세요, " + memName + "님.\n"
                    + "사원 등록이 완료되었습니다.\n\n"
                    + "임시 비밀번호: " + 1234 + "\n\n"
                    + "로그인 후 반드시 비밀번호를 변경해 주세요.";
        
        message.setText(text); 
        
        javaMailSender.send(message); 
        System.out.println("메일 전송 완료: " + memEmail);
    }

	public void resetPasswordEmail(String memEmail, String memPassword, String memName) {
		SimpleMailMessage message = new SimpleMailMessage();
		        
		        message.setTo(memEmail); 
		        message.setSubject("임시 비밀번호 변경 안내");
		        
		        String text = "안녕하세요, " + memName + "님.\n"
		                    + "비밀번호가 변경되었습니다.\n\n"
		                    + "임시 비밀번호: " + 1234 + "\n\n"
		                    + "로그인 후 반드시 비밀번호를 변경해 주세요.";
		        
		        message.setText(text);
		        
		        javaMailSender.send(message);
		        System.out.println("메일 전송 완료: " + memEmail);
	}
}
