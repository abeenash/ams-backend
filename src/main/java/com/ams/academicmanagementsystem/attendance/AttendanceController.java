package com.ams.academicmanagementsystem.attendance;

import com.ams.academicmanagementsystem.common.PageResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendance")
public class AttendanceController {
    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    @PostMapping
    public MarkAttendanceResponse markAttendance(@RequestBody MarkAttendanceRequest request) {
        return attendanceService.markAttendance(
                request.subjectId(),
                request.sectionId(),
                request.attendanceDate(),
                request.records()
        );
    }

    @GetMapping("/me")
    public PageResponse<MyAttendanceResponse> getMyAttendance(
            @RequestParam(required = false) Long subjectId,
            @PageableDefault(size = 20, sort = "attendanceDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return attendanceService.getMyAttendance(subjectId, pageable);
    }

    @GetMapping("/me/summary")
    public List<SubjectAttendanceSummary> getMySummary(){
        return attendanceService.summarizeMyAttendance();
    }

    @GetMapping("/class")
    public PageResponse<AttendanceResponse> getClassAttendance(
            @RequestParam Long subjectId,
            @RequestParam Long sectionId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @PageableDefault(size = 20, sort = "attendanceDate", direction = Sort.Direction.DESC) Pageable pageable) {
        return attendanceService.getClassAttendance(subjectId, sectionId, date, pageable);
    }
}
