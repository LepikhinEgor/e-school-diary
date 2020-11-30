package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.AttendanceRepository;
import com.scw.electronicgradebook.domain.dto.AttendanceDto;
import com.scw.electronicgradebook.domain.entities.Attendance;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.enums.SecurityRole;
import com.scw.electronicgradebook.domain.mappers.AttendanceMapper;
import com.scw.electronicgradebook.services.AttendanceService;
import com.scw.electronicgradebook.services.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public void create(AttendanceDto dto) {
        Attendance attendance = attendanceMapper.toEntity(dto, null);

        attendanceRepository.create(attendance);
    }

    @Override
    @Transactional
    public void update(AttendanceDto dto, Long id) {
        Attendance attendance = attendanceMapper.toEntity(dto, id);

        User currentUser = securityUtils.getCurrentUser();
        if (attendance.getLesson().getTeacher().equals(currentUser) ||
                securityUtils.hasRole(currentUser, SecurityRole.ROLE_ADMIN))
            attendanceRepository.update(attendance);
        else
            log.warn("Attempt to update attendance failed. Permission denied");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Attendance> foundAttendance = attendanceRepository.getById(id);

        if (foundAttendance.isPresent()) {
            User currentUser = securityUtils.getCurrentUser();
            Attendance attendance = foundAttendance.get();

            if (attendance.getLesson().getTeacher().equals(currentUser) ||
                    securityUtils.hasRole(currentUser, SecurityRole.ROLE_ADMIN))
                attendanceRepository.update(attendance);
            else
                log.warn("Attempt to delete attendance failed. Permission denied");
        }
    }

    @Override
    @Transactional
    public Optional<AttendanceDto> getById(Long id) {
        return attendanceRepository.getById(id)
                .map(attendanceMapper::toDto);
    }
}
