package com.github.gubbib.backend.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final ObjectMapper objectMapper;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        ErrorResponseDTO body = ErrorResponseDTO.of(
                ErrorCode.AUTH_UNAUTHORIZED,
                request.getRequestURI()
        );


        response.setStatus(ErrorCode.AUTH_UNAUTHORIZED.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        objectMapper.writeValue(response.getWriter(), body);
    }
}
