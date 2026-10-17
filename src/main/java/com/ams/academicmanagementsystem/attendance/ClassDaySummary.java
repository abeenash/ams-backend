package com.ams.academicmanagementsystem.attendance;

import java.time.LocalDate;

public record ClassDaySummary(
        LocalDate date,
        Long present,
        Long absent
) {
}
