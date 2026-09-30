package com.ams.academicmanagementsystem.subjectassignment;

import com.ams.academicmanagementsystem.section.SectionResponse;
import com.ams.academicmanagementsystem.subject.SubjectResponse;
import com.ams.academicmanagementsystem.teacher.TeacherResponse;

public record SubjectAssignmentResponse(
        Long id,
        TeacherResponse teacher,
        SubjectResponse subject,
        SectionResponse section
) { }
