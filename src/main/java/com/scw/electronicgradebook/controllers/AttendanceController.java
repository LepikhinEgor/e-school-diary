package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.AttendanceDto;
import com.scw.electronicgradebook.services.interfaces.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    @PreAuthorize("hasAuthority('ATTENDANCE_READ')")
    @GetMapping("/attendance/{attendance_id}")
    public AttendanceDto getAttendance(@PathVariable("attendance_id") Long attendanceId) {
        return attendanceService.getById(attendanceId).orElse(null);
    }

    @PreAuthorize("hasAuthority('ATTENDANCE_WRITE')")
    @PostMapping("/attendance")
    public ResponseEntity<Object> createAttendance(@RequestBody AttendanceDto dto) {
        attendanceService.create(dto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('ATTENDANCE_WRITE')")
    @PutMapping("/attendance/{attendance_id}")
    public void updateAttendance(@RequestBody AttendanceDto dto,
                                 @PathVariable("attendance_id") Long attendanceId) {
        attendanceService.update(dto, attendanceId);
    }

    @PreAuthorize("hasAuthority('ATTENDANCE_WRITE')")
    @DeleteMapping("/attendance/{attendance_id}")
    public void deleteAttendance(@PathVariable("attendance_id") Long attendanceId) {
        attendanceService.delete(attendanceId);
    }
}
