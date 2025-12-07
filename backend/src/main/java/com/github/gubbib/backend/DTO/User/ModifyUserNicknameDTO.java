package com.github.gubbib.backend.DTO.User;

import lombok.Builder;

@Builder
public record ModifyUserNicknameDTO(
        String modifyNick
) {
}
