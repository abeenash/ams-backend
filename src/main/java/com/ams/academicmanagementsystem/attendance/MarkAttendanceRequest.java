package com.ams.academicmanagementsystem.attendance;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record MarkAttendanceRequest(
        @NotNull Long subjectId,
        @NotNull Long sectionId,
        @NotNull LocalDate attendanceDate,
        @NotEmpty List<@Valid StudentAttendanceEntry> records) {
}
