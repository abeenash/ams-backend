package com.ams.academicmanagementsystem.subjectassignment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SubjectAssignmentRepository extends JpaRepository<SubjectAssignment, Long> {
    boolean existsByTeacherUserEmailAndSubjectIdAndSectionId(String email, Long subjectId, Long sectionId);

    @Query("""
            select new com.ams.academicmanagementsystem.subjectassignment.TeacherClassResponse(
                s.id, s.name, s.code, sec.id, sec.name,
                concat(p.code,' ',cast(b.intakeYear as string)),
                (select count(st) from Student st where st.section = sec),
                (select max(a.attendanceDate) from Attendance a where a.subject = s and a.student.section = sec)
            )
            from SubjectAssignment sa
            join sa.subject s
            join sa.section sec
            join sec.batch b
            join b.program p
            where sa.teacher.user.email = :email
            order by s.name, sec.name
            """)
    List<TeacherClassResponse> findClassesByTeacherEmail(@Param("email") String email);
}
