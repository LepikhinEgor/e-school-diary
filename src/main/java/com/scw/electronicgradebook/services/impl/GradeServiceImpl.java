package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.GradeRepository;
import com.scw.electronicgradebook.domain.dto.GradeDto;
import com.scw.electronicgradebook.domain.entities.Grade;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.mappers.GradeMapper;
import com.scw.electronicgradebook.services.GradeService;
import com.scw.electronicgradebook.services.SecurityUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.scw.electronicgradebook.domain.enums.SecurityRole.ROLE_ADMIN;

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeRepository gradeRepository;

    private final SecurityUtils securityUtils;

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

        User currentUser = securityUtils.getCurrentUser();
        if (grade.getExam().getExaminer().equals(currentUser) ||
                securityUtils.hasRole(currentUser, ROLE_ADMIN))
            gradeRepository.update(grade);
        else
            log.warn("Attempt to update grade failed. Permission denied");
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Grade> foundGrade = gradeRepository.getById(id);

        if (foundGrade.isPresent()) {
            User currentUser = securityUtils.getCurrentUser();
            Grade grade = foundGrade.get();

            if (grade.getExam().getExaminer().equals(currentUser) ||
                    securityUtils.hasRole(currentUser, ROLE_ADMIN))
                gradeRepository.delete(grade);
            else
                log.warn("Attempt to remove grade failed. Permission denied");
        }
    }

    @Override
    @Transactional
    public Optional<GradeDto> getById(Long id) {
        return gradeRepository.getById(id).map(gradeMapper::toDto);
    }
}
