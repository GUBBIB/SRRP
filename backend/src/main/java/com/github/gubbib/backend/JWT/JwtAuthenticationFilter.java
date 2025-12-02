package com.github.gubbib.backend.JWT;

import com.github.gubbib.backend.Security.CustomUserPrincipal;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if(header !=null && header.startsWith("Bearer ")){
            String token = header.substring(7);
            try {
                Jws<Claims> claims = jwtTokenProvider.parseToken(token);
                Long userId = Long.parseLong(claims.getBody().getSubject());

                CustomUserPrincipal principal = "";

                Authentication authentication = "";

            } catch (Exception e){
                // 추후 수정
            }
        }
        filterChain.doFilter(request, response);
    }
}
