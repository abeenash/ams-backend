package com.ams.academicmanagementsystem.teacher;

public record TeacherResponse(Long id, String fullName, String email) {
    public static TeacherResponse from(Teacher teacher) {
        return new TeacherResponse(teacher.getId(),
                teacher.getUser().getFullName(),
                teacher.getUser().getEmail());
    }
}
