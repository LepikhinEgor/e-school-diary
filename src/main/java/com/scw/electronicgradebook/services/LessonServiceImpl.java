package com.scw.electronicgradebook.services;

import com.scw.electronicgradebook.dao.LessonRepository;
import com.scw.electronicgradebook.domain.dto.LessonDto;
import com.scw.electronicgradebook.domain.entities.Lesson;
import com.scw.electronicgradebook.domain.mappers.LessonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class LessonServiceImpl implements LessonService {

    private LessonRepository lessonRepository;

    private LessonMapper lessonMapper;

    @Autowired
    public void setLessonMapper(LessonMapper lessonMapper) {
        this.lessonMapper = lessonMapper;
    }

    @Autowired
    public void setRepository(LessonRepository repository) {
        this.lessonRepository = repository;
    }

    @Override
    @Transactional
    public void create(LessonDto dto) {
        Lesson lesson = lessonMapper.toEntity(dto, null);

        lessonRepository.create(lesson);
    }

    @Override
    @Transactional
    public void update(LessonDto dto, Long id) {
        Lesson lesson = lessonMapper.toEntity(dto, id);

        lessonRepository.update(lesson);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Lesson> foundLesson = lessonRepository.getById(id);

        foundLesson.ifPresent(lesson -> lessonRepository.delete(lesson));
    }

    @Override
    @Transactional
    public Optional<LessonDto> getById(Long id) {
        Optional<Lesson> foundLesson = lessonRepository.getById(id);

        return foundLesson.map(lesson -> lessonMapper.toDto(lesson));
    }
}
