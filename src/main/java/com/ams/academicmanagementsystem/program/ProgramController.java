package com.ams.academicmanagementsystem.program;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/programs")
public class ProgramController {
    private final ProgramService programService;

    public ProgramController(ProgramService programService) {
        this.programService = programService;
    }

    @GetMapping
    public List<ProgramResponse> getAllPrograms() {
        return programService.getAllPrograms();
    }

    @PostMapping
    public ProgramResponse createProgram(@RequestBody CreateProgramRequest request) {
        return programService.createProgram(request.name(), request.code());
    }

    public record CreateProgramRequest(String name, String code) {
    }
}
