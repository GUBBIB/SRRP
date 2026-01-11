package com.github.gubbib.backend.Controller.Attendance;

import com.github.gubbib.backend.DTO.Attendance.AttendanceResponse;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.Attendance.AttendanceService;
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
public class AttendanceController {

    private final AttendanceService attendanceService;

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
