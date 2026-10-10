package com.ams.academicmanagementsystem.attendance;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    @EntityGraph(attributePaths = {"subject"})
    Page<Attendance> findByStudentId(Long studentId, Pageable pageable);

    @EntityGraph(attributePaths = {"subject"})
    Page<Attendance> findByStudentIdAndSubjectId(Long studentId, Long subjectId, Pageable pageable);

    @EntityGraph(attributePaths = {"student","student.user"})
    Page<Attendance> findBySubjectIdAndStudentSectionId(Long subjectId, Long sectionId, Pageable pageable);

    @EntityGraph(attributePaths = {"student","student.user"})
    Page<Attendance> findBySubjectIdAndStudentSectionIdAndAttendanceDate(Long subjectId, Long sectionId, LocalDate date, Pageable pageable);

    @Query("""
            select new com.ams.academicmanagementsystem.attendance.SubjectAttendanceSummary(s.id, s.name, s.code,
            sum(case when a.present = true then 1 else 0 end),
            count(a))
            from Attendance a
            join a.subject s
            where a.student.id = :studentId
            group by s.id, s.name, s.code
            order by s.name
            """)
    List<SubjectAttendanceSummary> summarizeByStudent(@Param("studentId") Long studentId);
}
