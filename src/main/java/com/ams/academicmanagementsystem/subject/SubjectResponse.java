package com.ams.academicmanagementsystem.subject;

import com.ams.academicmanagementsystem.program.ProgramResponse;

public record SubjectResponse(
        Long id,
        String name,
        String code,
        Integer semesterNumber,
        ProgramResponse program
) {
    public static SubjectResponse from (Subject subject){
        return new SubjectResponse(
                subject.getId(),
                subject.getName(),
                subject.getCode(),
                subject.getSemesterNumber(),
                ProgramResponse.from(subject.getProgram())
        );
    }
}
