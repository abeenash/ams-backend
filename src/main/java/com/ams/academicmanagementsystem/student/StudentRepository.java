package com.ams.academicmanagementsystem.student;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByUserId(Long userId);

    long countBySectionId(Long sectionId);

    @Query("""
            select coalesce(max(s.rollNumber), 0) from Student s where s.section.id = :sectionId
            """)
    int findMaxRollNumberBySectionId(@Param("sectionId") Long sectionId);

    @EntityGraph(attributePaths = {"user"})
    List<Student> findBySectionIdOrderByRollNumber(Long sectionId);
}
