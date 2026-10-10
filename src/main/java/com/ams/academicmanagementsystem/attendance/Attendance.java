package com.ams.academicmanagementsystem.attendance;

import com.ams.academicmanagementsystem.student.Student;
import com.ams.academicmanagementsystem.subject.Subject;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @Column(name = "attendance_date", nullable = false)
    private LocalDate attendanceDate;

    @Column(nullable = false)
    private boolean present;

    protected Attendance() {
    }

    public Attendance(Student student, Subject subject, LocalDate attendanceDate, boolean present) {
        this.student = student;
        this.subject = subject;
        this.attendanceDate = attendanceDate;
        this.present = present;
    }

    public Long getId() {
        return id;
    }

    public Student getStudent() {
        return student;
    }

    public Subject getSubject() {
        return subject;
    }

    public LocalDate getAttendanceDate() {
        return attendanceDate;
    }

    ;

    public boolean isPresent() {
        return present;
    }
}
