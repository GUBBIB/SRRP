package com.github.gubbib.backend.Repository.Attendance;

import com.github.gubbib.backend.Domain.Attendance.Attendance;
import com.github.gubbib.backend.Security.CustomUserPrincipal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    boolean existsByUserAndDate(CustomUserPrincipal userPrincipal, LocalDate date);
}
