package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.AttendanceRepository;
import com.scw.electronicgradebook.domain.dto.AttendanceDto;
import com.scw.electronicgradebook.domain.entities.Attendance;
import com.scw.electronicgradebook.domain.mappers.AttendanceMapper;
import com.scw.electronicgradebook.services.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;

    private final AttendanceMapper attendanceMapper;

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

        attendanceRepository.update(attendance);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Attendance> foundAttendance = attendanceRepository.getById(id);

        foundAttendance.ifPresent(attendanceRepository::delete);
    }

    @Override
    @Transactional
    public Optional<AttendanceDto> getById(Long id) {
        return attendanceRepository.getById(id)
                .map(attendanceMapper::toDto);
    }
}
