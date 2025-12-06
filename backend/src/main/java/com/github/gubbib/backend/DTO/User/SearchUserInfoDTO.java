package com.github.gubbib.backend.DTO.User;

import lombok.Builder;

@Builder
public record SearchUserInfoDTO(
        String email,
        String nickname,
        String name,
        String profile_image_url
) {
}
