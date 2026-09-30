package com.ams.academicmanagementsystem.subject;

import com.ams.academicmanagementsystem.program.Program;
import com.ams.academicmanagementsystem.program.ProgramRepository;
import com.ams.academicmanagementsystem.program.ProgramResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectService {
    private final SubjectRepository subjectRepository;
    private final ProgramRepository programRepository;

    public SubjectService(SubjectRepository subjectRepository, ProgramRepository programRepository) {
        this.subjectRepository = subjectRepository;
        this.programRepository = programRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public SubjectResponse createSubject(Long programId, Integer semesterNumber, String name, String code) {
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new IllegalArgumentException("No program with id " + programId));
        Subject saved = subjectRepository.save(new Subject(program, semesterNumber, name, code));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SubjectResponse> getAllSubjects(){
        return subjectRepository.findAll().stream().map(this::toResponse).toList();
    }

    private SubjectResponse toResponse(Subject subject){
        return SubjectResponse.from(subject);
    }
}
