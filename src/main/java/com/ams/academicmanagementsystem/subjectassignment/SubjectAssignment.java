package com.ams.academicmanagementsystem.subjectassignment;

import com.ams.academicmanagementsystem.section.Section;
import com.ams.academicmanagementsystem.subject.Subject;
import com.ams.academicmanagementsystem.teacher.Teacher;
import jakarta.persistence.*;

@Entity
@Table(name = "subject_assignment")
public class SubjectAssignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    protected SubjectAssignment() {
    }

    public SubjectAssignment(Teacher teacher, Subject subject, Section section) {
        this.teacher = teacher;
        this.subject = subject;
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public Subject getSubject() {
        return subject;
    }

    public Section getSection() {
        return section;
    }
}
