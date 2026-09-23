package com.ams.academicmanagementsystem.section;

import com.ams.academicmanagementsystem.batch.Batch;
import jakarta.persistence.*;

@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "batch_id", nullable = false)
    private Batch batch;

    @Column(nullable = false, length = 10)
    private String name;

    protected Section() {
    }

    public Section(Batch batch, String name) {
        this.batch = batch;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public Batch getBatch() {
        return batch;
    }

    public String getName() {
        return name;
    }
}
