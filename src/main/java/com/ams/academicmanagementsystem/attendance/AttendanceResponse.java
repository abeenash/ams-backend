package com.ams.academicmanagementsystem.attendance;

import java.time.LocalDate;

public record AttendanceResponse(
        Long id,
        Long studentId,
        String studentName,
        LocalDate attendanceDate,
        boolean present
) {
    public static AttendanceResponse from(Attendance attendance) {
        return new AttendanceResponse(
                attendance.getId(),
                attendance.getStudent().getId(),
                attendance.getStudent().getUser().getFullName(),
                attendance.getAttendanceDate(),
                attendance.isPresent()
        );
    }
}
