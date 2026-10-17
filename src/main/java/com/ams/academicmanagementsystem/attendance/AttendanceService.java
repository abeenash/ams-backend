package com.ams.academicmanagementsystem.attendance;

import com.ams.academicmanagementsystem.common.PageResponse;
import com.ams.academicmanagementsystem.exception.BusinessRuleViolationException;
import com.ams.academicmanagementsystem.exception.ResourceNotFoundException;
import com.ams.academicmanagementsystem.section.Section;
import com.ams.academicmanagementsystem.section.SectionRepository;
import com.ams.academicmanagementsystem.security.CurrentUserProvider;
import com.ams.academicmanagementsystem.student.Student;
import com.ams.academicmanagementsystem.student.StudentRepository;
import com.ams.academicmanagementsystem.subject.Subject;
import com.ams.academicmanagementsystem.subject.SubjectRepository;
import com.ams.academicmanagementsystem.subjectassignment.SubjectAssignmentRepository;
import com.ams.academicmanagementsystem.user.Role;
import com.ams.academicmanagementsystem.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AttendanceService {
    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;
    private final SectionRepository sectionRepository;
    private final SubjectAssignmentRepository subjectAssignmentRepository;
    private final CurrentUserProvider currentUserProvider;
    private static final ZoneId COLLEGE_ZONE = ZoneId.of("Asia/Kathmandu");

    private Pageable withStableOrder(Pageable pageable) {
        Sort stable = pageable.getSort().and(Sort.by(Sort.Direction.DESC, "id"));
        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), stable);
    }

    private void requireAssignmentIfTeacher(Long subjectId, Long sectionId) {
        User currentUser = currentUserProvider.getCurrentUser().getUser();
        if (currentUser.getRole() == Role.TEACHER) {
            boolean assigned = subjectAssignmentRepository.existsByTeacherUserEmailAndSubjectIdAndSectionId(currentUser.getEmail(), subjectId, sectionId);
            if (!assigned) {
                throw new AccessDeniedException("You are not assigned to teach this subject in this section");
            }
        }
    }

    private Student currentStudent() {
        Long userId = currentUserProvider.getCurrentUser().getUser().getId();
        return studentRepository.findByUserId(userId).orElseThrow(() -> new ResourceNotFoundException("No student profile for the current user"));
    }

    public AttendanceService(AttendanceRepository attendanceRepository, StudentRepository studentRepository,
                             SubjectRepository subjectRepository, SectionRepository sectionRepository,
                             SubjectAssignmentRepository subjectAssignmentRepository, CurrentUserProvider currentUserProvider) {
        this.attendanceRepository = attendanceRepository;
        this.studentRepository = studentRepository;
        this.subjectRepository = subjectRepository;
        this.sectionRepository = sectionRepository;
        this.subjectAssignmentRepository = subjectAssignmentRepository;
        this.currentUserProvider = currentUserProvider;
    }

    @PreAuthorize("hasRole('TEACHER')")
    @Transactional
    public MarkAttendanceResponse markAttendance(Long subjectId, Long sectionId, LocalDate attendanceDate, List<StudentAttendanceEntry> entries) {
        requireAssignmentIfTeacher(subjectId, sectionId);

        if (attendanceDate.isAfter(LocalDate.now(COLLEGE_ZONE))) {
            throw new BusinessRuleViolationException("Attendance cannot be marked for a future date");
        }

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("No subject with id " + subjectId));
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("No section with id " + sectionId));
        List<Long> studentIds = entries.stream().map(StudentAttendanceEntry::studentId).toList();

        if (new HashSet<>(studentIds).size() != studentIds.size()) {
            throw new BusinessRuleViolationException("The same student appears more than once");
        }
        List<Student> students = studentRepository.findAllById(studentIds);

        if (students.size() != studentIds.size()) {
            throw new ResourceNotFoundException("One or more students in the request do not exist");
        }

        Map<Long, Student> studentById = students.stream().collect(Collectors.toMap(Student::getId, s -> s));
        List<Attendance> attendanceRecords = entries.stream().map(entry -> {
            Student student = studentById.get(entry.studentId());
            if (!student.getSection().getId().equals(sectionId)) {
                throw new BusinessRuleViolationException("Student " + entry.studentId() + " does not belong to section " + sectionId);
            }
            return new Attendance(student, subject, attendanceDate, entry.present());
        }).toList();

        long rosterSize = studentRepository.countBySectionId(sectionId);
        if (attendanceRecords.size() != rosterSize) {
            throw new BusinessRuleViolationException("Attendance must cover every student in the section: expected " + rosterSize + " but received " + attendanceRecords.size());
        }

        List<Attendance> saved = attendanceRepository.saveAll(attendanceRecords);

        List<AttendanceResponse> responses = saved.stream().map(AttendanceResponse::from).toList();

        return new MarkAttendanceResponse(
                subject.getId(),
                subject.getName(),
                section.getId(),
                section.getName(),
                attendanceDate,
                responses
        );
    }

    @PreAuthorize("hasRole('STUDENT')")
    @Transactional(readOnly = true)
    public PageResponse<MyAttendanceResponse> getMyAttendance(Long subjectId, Pageable pageable) {
        Student student = currentStudent();
        Page<Attendance> page = (subjectId == null)
                ? attendanceRepository.findByStudentId(student.getId(), withStableOrder(pageable))
                : attendanceRepository.findByStudentIdAndSubjectId(student.getId(), subjectId, withStableOrder(pageable));
        return PageResponse.from(page.map(MyAttendanceResponse::from));
    }

    @PreAuthorize("hasRole('STUDENT')")
    @Transactional(readOnly = true)
    public List<SubjectAttendanceSummary> summarizeMyAttendance() {
        Student student = currentStudent();
        return attendanceRepository.summarizeByStudent(student.getId());
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @Transactional(readOnly = true)
    public PageResponse<AttendanceResponse> getClassAttendance(Long subjectId, Long sectionId, LocalDate date, Pageable pageable) {
        requireAssignmentIfTeacher(subjectId, sectionId);
        Pageable stable = withStableOrder(pageable);
        Page<Attendance> page = (date == null)
                ? attendanceRepository.findBySubjectIdAndStudentSectionId(subjectId, sectionId, stable)
                : attendanceRepository.findBySubjectIdAndStudentSectionIdAndAttendanceDate(subjectId, sectionId, date, stable);
        return PageResponse.from(page.map(AttendanceResponse::from));
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @Transactional(readOnly = true)
    public RosterResponse getRoster(Long subjectId, Long sectionId, LocalDate date) {
        requireAssignmentIfTeacher(subjectId, sectionId);

        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new ResourceNotFoundException("No subject with id " + subjectId));
        Section section = sectionRepository.findById(sectionId).orElseThrow(() -> new ResourceNotFoundException("No section with id " + sectionId));

        List<Student> students = studentRepository.findBySectionIdOrderByRollNumber(sectionId);

        Map<Long, Boolean> statusByStudentId = attendanceRepository.findBySubjectIdAndStudentSectionIdAndAttendanceDate(subjectId, sectionId, date).stream().collect(Collectors.toMap(a -> a.getStudent().getId(), Attendance::isPresent));

        List<RosterResponse.RosterStudent> rows = students.stream().map(s -> new RosterResponse.RosterStudent(
                s.getId(), s.getUser().getFullName(), s.getRollNumber(), statusByStudentId.get(s.getId()))).toList();

        String batchLabel = section.getBatch().getProgram().getCode() + " " + section.getBatch().getIntakeYear();

        return new RosterResponse(subject.getId(), subject.getName(), subject.getCode(), section.getId(), section.getName(), batchLabel, date, !statusByStudentId.isEmpty(), rows);
    }

    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    @Transactional(readOnly = true)
    public List<ClassDaySummary> getClassDays(Long subjectId, Long sectionId){
        requireAssignmentIfTeacher(subjectId, sectionId);
        return attendanceRepository.summarizeByDay(subjectId, sectionId);
    }
}
