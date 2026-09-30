package com.ams.academicmanagementsystem.teacher;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TeacherService {
    private final TeacherRepository teacherRepository;

    public TeacherService(TeacherRepository teacherRepository){
        this.teacherRepository = teacherRepository;
    }

    @Transactional(readOnly = true)
    public List<TeacherResponse> getAllTeachers(){
        return teacherRepository.findAll().stream().map(TeacherResponse::from).toList();
    }
}
