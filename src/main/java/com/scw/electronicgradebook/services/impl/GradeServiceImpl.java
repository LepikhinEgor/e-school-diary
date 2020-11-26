package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.GradeRepository;
import com.scw.electronicgradebook.domain.dto.GradeDto;
import com.scw.electronicgradebook.domain.entities.Grade;
import com.scw.electronicgradebook.domain.mappers.GradeMapper;
import com.scw.electronicgradebook.services.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    private final GradeMapper gradeMapper;

    @Override
    @Transactional
    public void create(GradeDto dto) {
        Grade grade = gradeMapper.toEntity(dto, null);

        gradeRepository.create(grade);
    }

    @Override
    @Transactional
    public void update(GradeDto dto, Long id) {
        Grade grade = gradeMapper.toEntity(dto, id);
        grade.setId(id);

        gradeRepository.update(grade);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Grade> foundGrade =  gradeRepository.getById(id);

        foundGrade.ifPresent(gradeRepository::delete);
    }

    @Override
    @Transactional
    public Optional<GradeDto> getById(Long id) {
        return gradeRepository.getById(id).map(gradeMapper::toDto);
    }
}
