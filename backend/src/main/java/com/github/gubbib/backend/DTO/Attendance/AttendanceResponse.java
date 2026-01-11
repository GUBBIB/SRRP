package com.github.gubbib.backend.DTO.Attendance;

import lombok.Builder;

@Builder
public record AttendanceResponse(
        String message,
        Long earnedPoint
) {
}
