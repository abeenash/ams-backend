package com.ams.academicmanagementsystem.section;

import com.ams.academicmanagementsystem.batch.BatchResponse;

public record SectionResponse(
        Long id,
        String name,
        BatchResponse batch
) {
    public static SectionResponse from(Section section) {
        return new SectionResponse(section.getId(),
                section.getName(),
                BatchResponse.from(section.getBatch()));
    }
}
