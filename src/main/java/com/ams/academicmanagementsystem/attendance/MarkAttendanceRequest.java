package com.ams.academicmanagementsystem.attendance;

import java.time.LocalDate;
import java.util.List;

public record MarkAttendanceRequest(
        Long subjectId,
        Long sectionId,
        LocalDate attendanceDate,
        List<StudentAttendanceEntry> records) { }
