package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.LessonRepository;
import com.scw.electronicgradebook.domain.dto.LessonDto;
import com.scw.electronicgradebook.domain.entities.Lesson;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.mappers.LessonMapper;
import com.scw.electronicgradebook.services.LessonService;
import com.scw.electronicgradebook.services.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.scw.electronicgradebook.domain.enums.SecurityRole.ROLE_ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    private final LessonMapper lessonMapper;

    private final SecurityUtils securityUtils;

    @Override
    @Transactional
    public void create(LessonDto dto) {
        Lesson lesson = lessonMapper.toEntity(dto, null);

        lessonRepository.create(lesson);
    }

    @Override
    @Transactional
    @PreAuthorize("dto.teacherId = securityUtils.currentUser")
    public void update(LessonDto dto, Long id) {
        Lesson lesson = lessonMapper.toEntity(dto, id);

        lessonRepository.update(lesson);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Lesson> foundLesson = lessonRepository.getById(id);

        if (foundLesson.isPresent()) {
            Lesson lesson = foundLesson.get();
            User currentUser = securityUtils.getCurrentUser();

            if (lesson.getTeacher().equals(securityUtils.getCurrentUser()) ||
                    securityUtils.hasRole(currentUser, ROLE_ADMIN))
                lessonRepository.delete(lesson);
            else
                log.warn("Attempt to remove lesson failed. Permission denied");
        }

        foundLesson.ifPresent(lessonRepository::delete);
    }

    @Override
    @Transactional
    public Optional<LessonDto> getById(Long id) {
        Optional<Lesson> foundLesson = lessonRepository.getById(id);

        return foundLesson.map(lessonMapper::toDto);
    }
}
