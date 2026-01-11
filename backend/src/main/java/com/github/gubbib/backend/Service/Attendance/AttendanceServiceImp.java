package com.github.gubbib.backend.Service.Attendance;

import com.github.gubbib.backend.DTO.Attendance.AttendanceResponse;
import com.github.gubbib.backend.Domain.Attendance.Attendance;
import com.github.gubbib.backend.Domain.User.User;
import com.github.gubbib.backend.Exception.ErrorCode;
import com.github.gubbib.backend.Exception.GlobalException;
import com.github.gubbib.backend.Repository.Attendance.AttendanceRepository;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import com.github.gubbib.backend.Service.PointLog.PointLogService;
import com.github.gubbib.backend.Service.User.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AttendanceServiceImp implements AttendanceService {

    private final UserService userService;
    private final PointLogService pointLogService;
    private final AttendanceRepository attendanceRepository;

    @Override
    public Long RandomPointGenerator() {
        SecureRandom random = new SecureRandom();
        Long max = 100L;
        Long min = 10L;

        return random.nextLong(max - min + 1) + min;
    }

    @Override
    public AttendanceResponse checkAttendance(CustomUserPrincipal userPrincipal) {
        User user = userService.checkUser(userPrincipal);

        LocalDate today = LocalDate.now();

        if(attendanceRepository.existsByUserAndDate(userPrincipal, today)) {
            throw new GlobalException(ErrorCode.ATTENDANCE_ALREADY_EXISTS);
        }

        Attendance attendance = Attendance.create(user, today);

        Long point = RandomPointGenerator();
        user.addPoint(point);

        pointLogService.earn(user, point, "출석 체크");

        attendanceRepository.save(attendance);

        return AttendanceResponse.builder()
                .message("출석 체크 완료!")
                .earnedPoint(point)
                .build();
    }
}
