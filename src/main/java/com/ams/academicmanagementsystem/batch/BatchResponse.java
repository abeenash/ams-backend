package com.ams.academicmanagementsystem.batch;

import com.ams.academicmanagementsystem.section.SectionResponse;

public record BatchResponse(
        Long id,
        String programName,
        String programCode,
        Integer intakeYear,
        Integer currentSemester
) {
    public static BatchResponse from(Batch batch){
        return new BatchResponse(
                batch.getId(),
                batch.getProgram().getName(),
                batch.getProgram().getCode(),
                batch.getIntakeYear(),
                batch.getCurrentSemester()
        );
    }
}
