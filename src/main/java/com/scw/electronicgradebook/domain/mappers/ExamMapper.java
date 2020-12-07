package com.scw.electronicgradebook.domain.mappers;

import com.scw.electronicgradebook.dao.ExamRepository;
import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.ExamDto;
import com.scw.electronicgradebook.domain.entities.Exam;
import com.scw.electronicgradebook.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExamMapper {

    private final UserRepository userRepository;

    private final ExamRepository examRepository;

    public Exam toEntity(ExamDto dto, Long id) {

        Exam exam = id != null ? findExam(id) : new Exam();
        User user = findUser(dto.getExaminerId());

        exam.setName(dto.getName());
        exam.setStartTime(new Date(dto.getStartTime()));
        exam.setEndTime(new Date(dto.getEndTime()));
        exam.setSubject(dto.getSubject());
        exam.setExaminer(user);

        return exam;
    }

    public ExamDto toDto(Exam exam) {
        ExamDto examDto = new ExamDto();
        examDto.setName(exam.getName());
        examDto.setStartTime(exam.getStartTime().getTime());
        examDto.setEndTime(exam.getEndTime().getTime());
        examDto.setExaminerId(exam.getExaminer().getId());
        examDto.setSubject(exam.getSubject());

        return examDto;
    }

    private User findUser(Long id) {
        Optional<User> student = userRepository.getById(id);
        if (student.isEmpty())
            throw new IllegalArgumentException("User with id " + id + " not found");

        return student.get();
    }

    private Exam findExam(Long examId) {
        Optional<Exam> exam = examRepository.getById(examId);

        if (exam.isEmpty())
            throw new IllegalArgumentException("Exam not found");

        return exam.get();
    }
}
