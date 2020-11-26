package com.scw.electronicgradebook.services;

import com.scw.electronicgradebook.domain.dto.GradeDto;

import java.util.Optional;

public interface GradeService {

    void create(GradeDto dto);

    void update(GradeDto dto, Long id);

    void delete(Long id);

    Optional<GradeDto> getById(Long id);
}
