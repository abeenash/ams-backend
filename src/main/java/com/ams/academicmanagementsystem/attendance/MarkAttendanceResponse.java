package com.ams.academicmanagementsystem.attendance;

import java.time.LocalDate;
import java.util.List;

public record MarkAttendanceResponse(
        Long subjectId,
        String subjectName,
        Long sectionId,
        String sectionName,
        LocalDate attendanceDate,
        List<AttendanceResponse> records
) { }
