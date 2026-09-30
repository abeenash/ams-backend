package com.ams.academicmanagementsystem.student;

import com.ams.academicmanagementsystem.user.User;
import com.ams.academicmanagementsystem.section.Section;
import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "section_id", nullable = false)
    private Section section;

    protected Student() {
    }

    public Student(User user, Section section) {
        this.user = user;
        this.section = section;
    }

    public Long getId() {
        return id;
    }

    public User getUser() {
        return user;
    }

    public Section getSection() {
        return section;
    }
}
