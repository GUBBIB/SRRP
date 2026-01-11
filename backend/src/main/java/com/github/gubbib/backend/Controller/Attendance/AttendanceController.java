package com.github.gubbib.backend.Controller.Attendance;

import com.github.gubbib.backend.DTO.Attendance.AttendanceResponse;
import com.github.gubbib.backend.DTO.Error.ErrorResponseDTO;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Attendance.AttendanceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
@RequestMapping("/api/v1/attendance")
@Tag(name = "출석체크", description = "출석 관련 API")
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Operation(
            summary = "출석 체크",
            description = "로그인한 사용자가 오늘 날짜로 출석 체크를 진행합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "출석 체크 성공",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = AttendanceResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = "인증되지 않은 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "409",
                    description = "이미 오늘 출석을 완료한 사용자",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "서버 내부 오류",
                    content = @Content(
                            mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponseDTO.class)
                    )
            )
    })
    @PostMapping("/check")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<AttendanceResponse> checkAttendance(
            @AuthenticationPrincipal CustomUserPrincipal userPrincipal
    ) {
        AttendanceResponse response = attendanceService.checkAttendance(userPrincipal);

        return  ResponseEntity.ok()
                .body(response);
    }
}
