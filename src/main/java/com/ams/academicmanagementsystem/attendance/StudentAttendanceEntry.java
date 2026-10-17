package com.ams.academicmanagementsystem.attendance;

import jakarta.validation.constraints.NotNull;

public record StudentAttendanceEntry(
        @NotNull Long studentId,
        @NotNull Boolean present) {
}
