package com.ams.academicmanagementsystem.subjectassignment;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject-assignments")
public class SubjectAssignmentController {
    private final SubjectAssignmentService subjectAssignmentService;

    public SubjectAssignmentController(SubjectAssignmentService subjectAssignmentService){
        this.subjectAssignmentService = subjectAssignmentService;
    }

    @GetMapping
    public List<SubjectAssignmentResponse> getAllAssignments(){
        return subjectAssignmentService.getAllAssignments();
    }

    @PostMapping
    public SubjectAssignmentResponse createAssignment(@RequestBody CreateSubjectAssignmentRequest request){
        return subjectAssignmentService.createAssignment(request.teacherId(), request.subjectId(), request.sectionId());
    }

    public record CreateSubjectAssignmentRequest(Long teacherId, Long subjectId, Long sectionId){}
}
