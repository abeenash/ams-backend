package com.ams.academicmanagementsystem.attendance;

public record SubjectAttendanceSummary(
        Long subjectId,
        String subjectName,
        String subjectCode,
        String teacherName,
        Long present,
        Long total
) {
}
