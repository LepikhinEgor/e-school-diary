package com.scw.electronicgradebook.services.interfaces;

import com.scw.electronicgradebook.domain.dto.AttendanceDto;

import java.util.Optional;

public interface AttendanceService {

    void create(AttendanceDto dto);

    void update(AttendanceDto dto, Long id);

    void delete(Long id);

    Optional<AttendanceDto> getById(Long id);
}
