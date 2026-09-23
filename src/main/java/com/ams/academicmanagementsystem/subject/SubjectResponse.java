package com.ams.academicmanagementsystem.subject;

import com.ams.academicmanagementsystem.program.ProgramResponse;

public record SubjectResponse(
        Long id,
        String name,
        String code,
        Integer semesterNumber,
        ProgramResponse program
) {}
