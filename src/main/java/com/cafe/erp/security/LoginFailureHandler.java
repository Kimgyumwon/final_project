package com.cafe.erp.security;

import java.io.IOException;
import java.net.URLEncoder;

import org.springframework.security.core.AuthenticationException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import com.cafe.erp.member.history.MemberHistoryService;
import com.cafe.erp.member.history.MemberLoginHistoryDTO;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class LoginFailureHandler implements AuthenticationFailureHandler{

	@Autowired
	private MemberHistoryService memberHistoryService;

	
	@Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException exception) throws IOException, ServletException {
		
				String loginId = request.getParameter("memberId");
		
		
		        try {
		        	MemberLoginHistoryDTO history = new MemberLoginHistoryDTO();
		        	history.setMemLogHisClientIp(request.getRemoteAddr());
		        	history.setMemLogHisRequestUrl(request.getRequestURI());
		        	history.setMemLogHisActionType("LOGIN_FAIL");
		        	history.setMemLogHisIsSuccess(false);
		        	history.setMemLogHisLoginId(loginId);
		        	memberHistoryService.insertLoginHistory(history);
		        } catch (Exception e) {
		            e.printStackTrace();
		        }
		        
		        String message;
		        
		        if (exception instanceof org.springframework.security.authentication.LockedException) {
		            message = "퇴직 처리되었거나 잠긴 계정입니다.";
		        } else if (exception instanceof org.springframework.security.authentication.DisabledException) {
		            message = "비활성화된 계정입니다.";
		        } else if (exception instanceof org.springframework.security.authentication.BadCredentialsException) {
		            message = "아이디 또는 비밀번호가 틀렸습니다.";
		        } else {
		            message = "로그인에 실패했습니다.";
		        }

		        response.sendRedirect("/member/login?error=true&message=" + URLEncoder.encode(message, "UTF-8"));
			}
			
		}
