package com.github.gubbib.backend.Service.Attendance;

import com.github.gubbib.backend.DTO.Attendance.AttendanceResponse;
import com.github.gubbib.backend.Security.CustomUserPrincipal;

public interface AttendanceService {

    Long RandomPointGenerator();
    AttendanceResponse checkAttendance(CustomUserPrincipal userPrincipal);
}
