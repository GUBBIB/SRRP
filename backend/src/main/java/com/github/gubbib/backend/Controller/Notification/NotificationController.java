package com.github.gubbib.backend.Controller.Notification;

import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import com.github.gubbib.backend.DTO.Notification.UserMyNotificationDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Notification.NotificationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/notifications")
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@Tag(name = "알림", description = "알림 관련 api")
public class NotificationController {

    private final NotificationService notificationService;

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = UserMyNotificationDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증 필요",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = "사용자 없음",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @GetMapping("/my")
    public ResponseEntity<List<UserMyNotificationDTO>> getMy(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ){
        List<UserMyNotificationDTO> response = notificationService.getMy(userPrincipal);

        return ResponseEntity.ok()
                .body(response);
    }

}
