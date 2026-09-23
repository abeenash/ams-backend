package com.ams.academicmanagementsystem.program;

public record ProgramResponse(Long id, String name, String code) {
    public static ProgramResponse from(Program program) {
        return new ProgramResponse(program.getId(), program.getName(), program.getCode());
    }
}
