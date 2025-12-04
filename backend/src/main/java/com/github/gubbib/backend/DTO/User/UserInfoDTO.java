package com.github.gubbib.backend.DTO.User;

import lombok.Builder;
import lombok.Getter;

@Builder
public record UserInfoDTO(
        String email,
        String nickname,
        String name,
        String profile_image_url
) {
}
