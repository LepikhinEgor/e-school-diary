package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.ExamRepository;
import com.scw.electronicgradebook.domain.dto.ExamDto;
import com.scw.electronicgradebook.domain.entities.Exam;
import com.scw.electronicgradebook.domain.mappers.ExamMapper;
import com.scw.electronicgradebook.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    private final ExamMapper examMapper;

    @Override
    @Transactional
    public void create(ExamDto dto) {
        Exam exam = examMapper.toEntity(dto, null);

        examRepository.create(exam);
    }

    @Override
    @Transactional
    public void update(ExamDto dto, Long id) {
        Exam exam = examMapper.toEntity(dto, id);

        examRepository.update(exam);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Exam> foundExam = examRepository.getById(id);

        foundExam.ifPresent(examRepository::delete);
    }

    @Override
    @Transactional
    public Optional<ExamDto> getById(Long id) {
        return examRepository.getById(id).map(examMapper::toDto);
    }
}
