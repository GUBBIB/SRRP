package com.github.gubbib.backend.DTO.Notification;

import com.github.gubbib.backend.Domain.User.User;
import lombok.Builder;

@Builder
public record CommentNotificationEventDTO (
        User receiver,
        User sender,
        Long postId
){

}
