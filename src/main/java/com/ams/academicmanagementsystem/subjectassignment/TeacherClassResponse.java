package com.ams.academicmanagementsystem.subjectassignment;

import java.time.LocalDate;

public record TeacherClassResponse(
        Long subjectId,
        String subjectName,
        String subjectCode,
        Long sectionId,
        String sectionName,
        String batchLabel,
        Long studentCount,
        LocalDate lastMarkedDate
) {
}
