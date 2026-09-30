package com.ams.academicmanagementsystem.subjectassignment;

import com.ams.academicmanagementsystem.section.Section;
import com.ams.academicmanagementsystem.section.SectionRepository;
import com.ams.academicmanagementsystem.section.SectionResponse;
import com.ams.academicmanagementsystem.subject.Subject;
import com.ams.academicmanagementsystem.subject.SubjectRepository;
import com.ams.academicmanagementsystem.subject.SubjectResponse;
import com.ams.academicmanagementsystem.teacher.Teacher;
import com.ams.academicmanagementsystem.teacher.TeacherRepository;
import com.ams.academicmanagementsystem.teacher.TeacherResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SubjectAssignmentService {
    private final SubjectAssignmentRepository subjectAssignmentRepository;
    private final TeacherRepository teacherRepository;
    private final SectionRepository sectionRepository;
    private final SubjectRepository subjectRepository;

    public SubjectAssignmentService(SubjectAssignmentRepository subjectAssignmentRepository,
                             TeacherRepository teacherRepository, SubjectRepository subjectRepository,
                             SectionRepository sectionRepository){
        this.subjectAssignmentRepository = subjectAssignmentRepository;
        this.teacherRepository = teacherRepository;
        this.subjectRepository = subjectRepository;
        this.sectionRepository = sectionRepository;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public SubjectAssignmentResponse createAssignment(Long teacherId, Long subjectId, Long sectionId){
        Teacher teacher = teacherRepository.findById(teacherId).orElseThrow(()->new IllegalArgumentException("No teacher with id "+teacherId));
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(()->new IllegalArgumentException("No subject with id "+subjectId));
        Section section = sectionRepository.findById(sectionId).orElseThrow(()->new IllegalArgumentException("No section with id "+sectionId));
        SubjectAssignment saved = subjectAssignmentRepository.save(new SubjectAssignment(teacher, subject, section));
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public List<SubjectAssignmentResponse> getAllAssignments(){
        return subjectAssignmentRepository.findAll().stream().map(this::toResponse).toList();
    }

    private SubjectAssignmentResponse toResponse(SubjectAssignment assignment){
        return new SubjectAssignmentResponse(
                assignment.getId(),
                TeacherResponse.from(assignment.getTeacher()),
                SubjectResponse.from(assignment.getSubject()),
                SectionResponse.from(assignment.getSection())
        );
    }
}
