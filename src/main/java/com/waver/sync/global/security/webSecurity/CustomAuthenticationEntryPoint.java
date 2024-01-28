package com.waver.sync.global.security.webSecurity;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        String exception = (String) request.getAttribute("exception");

        if (exception.equals("토큰을 발견하지 못했습니다.")) {
            exceptionHandler(response, "토큰을 발견하지 못했습니다.");
            return;
        }

        if (exception.equals("유효한 토큰이 아닙니다.")) {
            exceptionHandler(response, "유효한 토큰이 아닙니다.");
            return;
        }

        if (exception.equals("유저를 찾지 못했습니다.")) {
            exceptionHandler(response, "유저를 찾지 못했습니다.");
        }
    }

    public void exceptionHandler(HttpServletResponse response, String error) {
        response.setStatus(400);
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        try {
            String json = new ObjectMapper().writeValueAsString(error);
            response.getWriter().write(json);
            log.error(error);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
