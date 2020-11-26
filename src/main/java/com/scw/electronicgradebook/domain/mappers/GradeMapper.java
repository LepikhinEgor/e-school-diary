package com.scw.electronicgradebook.domain.mappers;

import com.scw.electronicgradebook.dao.ExamRepository;
import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.GradeDto;
import com.scw.electronicgradebook.domain.entities.Exam;
import com.scw.electronicgradebook.domain.entities.Grade;
import com.scw.electronicgradebook.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GradeMapper {

    private final UserRepository userRepository;

    private final ExamRepository examRepository;

    public GradeDto toDto(Grade grade) {
        GradeDto dto = new GradeDto();
        dto.setExamId(grade.getExam().getId());
        dto.setStudentId(grade.getStudent().getId());
        dto.setValue(grade.getValue());

        return dto;
    }

    public Grade toEntity(GradeDto dto, Long id) {
        Grade grade = new Grade();

        Exam exam = findExam(dto.getExamId());
        User user = findUser(dto.getExamId());

        grade.setId(id);
        grade.setExam(exam);
        grade.setStudent(user);
        grade.setValue(dto.getValue());

        return grade;
    }

    private Exam findExam(Long id) {
        Optional<Exam> exam = examRepository.getById(id);
        if (exam.isEmpty())
            throw new IllegalArgumentException("Exam with id " + id + " not found");

        return exam.get();
    }

    private User findUser(Long id) {
        Optional<User> student = userRepository.getById(id);
        if (student.isEmpty())
            throw new IllegalArgumentException("User with id " + id + " not found");

        return student.get();
    }
}
