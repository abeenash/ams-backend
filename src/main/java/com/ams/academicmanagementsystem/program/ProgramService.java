package com.ams.academicmanagementsystem.program;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramService {

    private final ProgramRepository programRepository;

    public ProgramService(ProgramRepository programRepository) {
        this.programRepository = programRepository;
    }

    @Transactional(readOnly = true)
    public List<ProgramResponse> getAllPrograms() {
        return programRepository.findAll().stream().map(ProgramResponse::from).toList();
    }

    @Transactional
    public ProgramResponse createProgram(String name, String code) {
        Program saved = programRepository.save(new Program(name,code));
        return ProgramResponse.from(saved);
    }
}
