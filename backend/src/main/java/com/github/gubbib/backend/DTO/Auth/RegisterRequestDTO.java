package com.github.gubbib.backend.DTO.Auth;

public record RegisterRequestDTO(
        String email,
        String password,
        String name,
        String nickname
){
}
