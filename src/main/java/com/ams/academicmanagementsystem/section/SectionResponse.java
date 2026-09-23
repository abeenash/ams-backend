package com.ams.academicmanagementsystem.section;

import com.ams.academicmanagementsystem.batch.BatchResponse;

public record SectionResponse(
        Long id,
        String name,
        BatchResponse batch
) { }
