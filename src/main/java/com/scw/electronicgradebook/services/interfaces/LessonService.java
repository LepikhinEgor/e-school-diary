package com.scw.electronicgradebook.services.interfaces;

import com.scw.electronicgradebook.domain.dto.LessonDto;

import java.util.Optional;

public interface LessonService {

    void create(LessonDto lesson);

    void update(LessonDto lesson, Long id);

    void delete(Long id);

    Optional<LessonDto> getById(Long id);
}
