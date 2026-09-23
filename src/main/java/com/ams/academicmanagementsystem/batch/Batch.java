package com.ams.academicmanagementsystem.batch;

import com.ams.academicmanagementsystem.program.Program;
import jakarta.persistence.*;

@Entity
@Table(name = "batch")
public class Batch {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "program_id", nullable = false)
    private Program program;

    @Column(name = "intake_year", nullable = false)
    private Integer intakeYear;

    @Column(name = "current_semester", nullable = false)
    private Integer currentSemester;

    protected Batch() {
    }

    public Batch(Program program, Integer intakeYear, Integer currentSemester) {
        this.program = program;
        this.intakeYear = intakeYear;
        this.currentSemester = currentSemester;
    }

    public Long getId() {
        return id;
    }

    public Program getProgram() {
        return program;
    }

    public Integer getIntakeYear() {
        return intakeYear;
    }

    public Integer getCurrentSemester() {
        return currentSemester;
    }
}
