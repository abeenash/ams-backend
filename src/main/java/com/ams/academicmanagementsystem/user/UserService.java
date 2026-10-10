package com.ams.academicmanagementsystem.user;

import com.ams.academicmanagementsystem.exception.ResourceNotFoundException;
import com.ams.academicmanagementsystem.section.Section;
import com.ams.academicmanagementsystem.section.SectionRepository;
import com.ams.academicmanagementsystem.student.Student;
import com.ams.academicmanagementsystem.student.StudentRepository;
import com.ams.academicmanagementsystem.teacher.Teacher;
import com.ams.academicmanagementsystem.teacher.TeacherRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final SectionRepository sectionRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, StudentRepository studentRepository,
                       TeacherRepository teacherRepository, SectionRepository sectionRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.sectionRepository = sectionRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public UserResponse register(String email, String rawPassword, String fullName, Role role, Long sectionId) {
        String passwordHash = passwordEncoder.encode(rawPassword);
        User user = userRepository.save(new User(email, passwordHash, fullName, role));
        if (role == Role.STUDENT) {
            Section section = sectionRepository.findById(sectionId)
                    .orElseThrow(() -> new ResourceNotFoundException("No section with id " + sectionId));
            studentRepository.save(new Student(user, section));
        } else if (role == Role.TEACHER) {
            teacherRepository.save(new Teacher(user));
        }
        return UserResponse.from(user);
    }
}
