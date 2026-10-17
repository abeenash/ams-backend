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

    @EntityGraph(attributePaths = {"student", "student.user"})
    Page<Attendance> findBySubjectIdAndStudentSectionId(Long subjectId, Long sectionId, Pageable pageable);

    @EntityGraph(attributePaths = {"student", "student.user"})
    Page<Attendance> findBySubjectIdAndStudentSectionIdAndAttendanceDate(Long subjectId, Long sectionId, LocalDate date, Pageable pageable);

    List<Attendance> findBySubjectIdAndStudentSectionIdAndAttendanceDate(Long subjectId, Long sectionId, LocalDate date);

    @Query("""
            select new com.ams.academicmanagementsystem.attendance.SubjectAttendanceSummary(s.id, s.name, s.code, u.fullName,
            sum(case when a.present = true then 1 else 0 end),
            count(a))
            from Attendance a
            join a.subject s
            join a.student st
            left join SubjectAssignment sa on sa.subject = s and sa.section = st.section
            left join sa.teacher t
            left join t.user u
            where st.id = :studentId
            group by s.id, s.name, s.code, u.fullName
            order by s.name
            """)
    List<SubjectAttendanceSummary> summarizeByStudent(@Param("studentId") Long studentId);

    @Query("""
            select new com.ams.academicmanagementsystem.attendance.ClassDaySummary(
            a.attendanceDate,
            sum(case when a.present = true then 1 else 0 end),
            sum(case when a.present = false then 1 else 0 end))
            from Attendance a
            where a.subject.id = :subjectId and a.student.section.id = :sectionId
            group by a.attendanceDate
            order by a.attendanceDate desc
            """)
    List<ClassDaySummary> summarizeByDay(@Param("subjectId") Long subjectId, @Param("sectionId") Long sectionId);
}
