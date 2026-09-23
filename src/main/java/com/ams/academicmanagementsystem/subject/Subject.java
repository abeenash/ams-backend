package com.ams.academicmanagementsystem.subject;

import com.ams.academicmanagementsystem.program.Program;
import jakarta.persistence.*;

@Entity
@Table(name = "subject")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "semester_number", nullable = false)
    private Integer semesterNumber;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 20)
    private String code;

    protected Subject() {
    }

    public Subject(Program program, Integer semesterNumber, String name, String code) {
        this.program = program;
        this.semesterNumber = semesterNumber;
        this.name = name;
        this.code = code;
    }

    public Long getId() {
        return id;
    }

    public Program getProgram() {
        return program;
    }

    public Integer getSemesterNumber() {
        return semesterNumber;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

}
