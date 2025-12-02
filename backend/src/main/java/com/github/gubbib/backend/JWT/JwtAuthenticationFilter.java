package com.github.gubbib.backend.JWT;

import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.User.UserNotFoundException;
import com.github.gubbib.backend.Repository.User.UserRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Auth.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

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

                User user = userRepository.findById(userId)
                        .orElseThrow(UserNotFoundException::new);

                CustomUserPrincipal principal = new CustomUserPrincipal(user);

                Authentication authentication = new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());

                SecurityContextHolder.getContext().setAuthentication(authentication);

            } catch (Exception e){
                // 추후 수정
            }
        }
        filterChain.doFilter(request, response);
    }
}
