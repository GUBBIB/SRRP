package com.github.gubbib.backend.Exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {

        ErrorResponseDTO body = ErrorResponseDTO.of(
                ErrorCode.AUTH_FORBIDDEN,
                request.getRequestURI()
        );

        response.setStatus(ErrorCode.AUTH_FORBIDDEN.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        objectMapper.writeValue(response.getWriter(), body);

    }
}
