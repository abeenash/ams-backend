package com.ams.academicmanagementsystem.attendance;

import java.time.LocalDate;
import java.util.List;

public record RosterResponse(
        Long subjectId,
        String subjectName,
        String subjectCode,
        Long sectionId,
        String sectionName,
        String batchLabel,
        LocalDate date,
        boolean recorded,
        List<RosterStudent> students
) {
    public record RosterStudent(Long studentId, String fullName, Integer rollNumber, Boolean present) {
    }
}
