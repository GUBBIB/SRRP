package com.github.gubbib.backend.DTO.User;

import lombok.Builder;

@Builder
public record ModifyUserPasswordDTO(
        String currentPassword,
        String modifyPassword
) {
}
