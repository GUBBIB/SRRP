package com.github.gubbib.backend.DTO.Auth;

public record LoginRequestDTO (
        String email,
        String password
){
}
