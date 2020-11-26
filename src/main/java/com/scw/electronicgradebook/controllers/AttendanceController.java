package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.AttendanceDto;
import com.scw.electronicgradebook.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @Secured("ATTENDANCE_READ")
    @GetMapping("/attendance/{attendance_id}")
    public AttendanceDto getAttendance(@PathVariable("attendance_id") Long attendanceId) {
        return attendanceService.getById(attendanceId).orElse(null);
    }

    @Secured("ATTENDANCE_WRITE")
    @PostMapping("/attendance")
    public void createAttendance(@RequestBody AttendanceDto dto) {
        attendanceService.create(dto);
    }

    @Secured("ATTENDANCE_WRITE")
    @PutMapping("/attendance/{attendance_id}")
    public void updateAttendance(@RequestBody AttendanceDto dto,
                                 @PathVariable("attendance_id") Long attendanceId) {
        attendanceService.update(dto, attendanceId);
    }

    @Secured("ATTENDANCE_WRITE")
    @DeleteMapping("/attendance/{attendance_id}")
    public void deleteAttendance(@PathVariable("attendance_id") Long attendanceId) {
        attendanceService.delete(attendanceId);
    }
}
