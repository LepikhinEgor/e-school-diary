package com.scw.electronicgradebook.services.interfaces;

import com.scw.electronicgradebook.domain.dto.ExamDto;

import java.util.Optional;

public interface ExamService {

    void create(ExamDto dto);

    void update(ExamDto dto, Long id);

    void delete(Long id);

    Optional<ExamDto> getById(Long id);
}
