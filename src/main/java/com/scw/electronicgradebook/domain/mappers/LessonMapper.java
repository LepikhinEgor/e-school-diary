package com.scw.electronicgradebook.domain.mappers;

import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.LessonDto;
import com.scw.electronicgradebook.domain.entities.Lesson;
import com.scw.electronicgradebook.domain.entities.User;
import com.scw.electronicgradebook.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class LessonMapper {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Lesson toEntity(LessonDto dto, Long id) {
        Lesson lesson = new Lesson();
        lesson.setId(id);
        lesson.setStartTime(new Date(dto.getStartTime()));
        lesson.setEndTime(new Date(dto.getEndTime()));
        lesson.setTeacher(getTeacherId(dto.getTeacherId()));

        return lesson;
    }

    public LessonDto toDto(Lesson lesson) {
        LessonDto lessonDto = new LessonDto();
        lessonDto.setStartTime(lesson.getStartTime().getTime());
        lessonDto.setEndTime(lesson.getEndTime().getTime());
        lessonDto.setTeacherId(lesson.getTeacher().getId());

        return lessonDto;
    }

    private User getTeacherId(Long id) {
        Optional<User> teacher = userRepository.getById(id);

        return teacher.orElseThrow(() -> new IllegalArgumentException("Teacher not found"));
    }
}
