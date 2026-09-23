package com.ams.academicmanagementsystem.subject;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subjects")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<SubjectResponse> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @PostMapping
    public SubjectResponse createSubject(@RequestBody CreateSubjectRequest request) {
        return subjectService.createSubject(request.programId(), request.semesterNumber(), request.name(), request.code());
    }

    public record CreateSubjectRequest(Long programId, Integer semesterNumber, String name, String code) {
    }
}
