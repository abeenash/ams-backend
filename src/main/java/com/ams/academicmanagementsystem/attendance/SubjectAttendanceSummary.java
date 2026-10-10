package com.ams.academicmanagementsystem.attendance;

public record SubjectAttendanceSummary(
        Long subjectId,
        String subjectName,
        String subjectCode,
        Long present,
        Long total
) {
}
