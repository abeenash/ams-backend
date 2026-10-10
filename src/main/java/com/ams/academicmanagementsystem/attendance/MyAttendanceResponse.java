package com.ams.academicmanagementsystem.attendance;

import java.time.LocalDate;

public record MyAttendanceResponse(
        Long id,
        Long subjectId,
        String subjectName,
        String subjectCode,
        LocalDate attendanceDate,
        boolean present
) {
    public static MyAttendanceResponse from(Attendance attendance){
        return new MyAttendanceResponse(
                attendance.getId(),
                attendance.getSubject().getId(),
                attendance.getSubject().getName(),
                attendance.getSubject().getCode(),
                attendance.getAttendanceDate(),
                attendance.isPresent()
        );
    }
}
