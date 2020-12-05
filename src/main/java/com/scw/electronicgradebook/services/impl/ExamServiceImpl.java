package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.dao.ExamRepository;
import com.scw.electronicgradebook.domain.dto.ExamDto;
import com.scw.electronicgradebook.domain.entities.Exam;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.domain.enums.SecurityRole;
import com.scw.electronicgradebook.domain.mappers.ExamMapper;
import com.scw.electronicgradebook.services.interfaces.ExamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamServiceImpl implements ExamService {

    private final ExamRepository examRepository;

    private final SecurityUtils securityUtils;

    private final ExamMapper examMapper;

    @Override
    @Transactional
    public void create(ExamDto dto) {
        Exam exam = examMapper.toEntity(dto, null);

        exam.setLastUpdated(new Date());
        exam.setApprovedByAdmin(false);
        examRepository.create(exam);
    }

    @Override
    @Transactional
    @PreAuthorize("dto.examinerId == securityUtils.currentUser")
    public void update(ExamDto dto, Long id) {
        Exam exam = examMapper.toEntity(dto, id);

        exam.setLastUpdated(new Date());

        examRepository.update(exam);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Optional<Exam> foundExam = examRepository.getById(id);

        User currentUser = securityUtils.getCurrentUser();
        if (foundExam.isPresent()) {
            if (foundExam.get().getExaminer().equals(currentUser)
                    || securityUtils.hasRole(currentUser, SecurityRole.ROLE_ADMIN))
                examRepository.delete(foundExam.get());
            else
                log.warn("Attempt to remove exam failed. Permission denied");
        }
    }

    @Override
    @Transactional
    public Optional<ExamDto> getById(Long id) {
        return examRepository.getById(id).map(examMapper::toDto);
    }

}
