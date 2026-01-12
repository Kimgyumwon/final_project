package com.cafe.erp.security;

import com.cafe.erp.store.StoreDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        UserDTO user = (UserDTO) authentication.getPrincipal();
        String memberIdStr = user.getUsername();
        StoreDTO storeDTO = user.getStore();

        if (memberIdStr.startsWith("2")) { // 가맹 점주
            if (storeDTO != null) response.sendRedirect("/store/detail?storeId=" + storeDTO.getStoreId());
            else response.sendRedirect("/store/notFound"); // 가맹점 정보가 아직 생성되지 않은 점주
        } else { // 본사 직원
            response.sendRedirect("/member/AM_group_chart");
        }
    }

}
