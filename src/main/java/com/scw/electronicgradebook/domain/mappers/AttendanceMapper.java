package com.scw.electronicgradebook.domain.mappers;

import com.scw.electronicgradebook.dao.LessonRepository;
import com.scw.electronicgradebook.dao.UserRepository;
import com.scw.electronicgradebook.domain.dto.AttendanceDto;
import com.scw.electronicgradebook.domain.entities.Attendance;
import com.scw.electronicgradebook.domain.entities.Lesson;
import com.scw.electronicgradebook.domain.entities.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttendanceMapper {

    private final UserRepository userRepository;

    private final LessonRepository lessonRepository;

    public Attendance toEntity(AttendanceDto dto, Long id) {
        Attendance attendance = new Attendance();

        Lesson lesson = findLesson(dto.getLessonId());
        User student = findUser(dto.getStudentId());

        attendance.setId(id);
        attendance.setLesson(lesson);
        attendance.setStudent(student);
        attendance.setVisited(dto.getVisited());

        return attendance;
    }

    public AttendanceDto toDto(Attendance attendance) {
        AttendanceDto dto = new AttendanceDto();
        dto.setLessonId(attendance.getLesson().getId());
        dto.setStudentId(attendance.getStudent().getId());
        dto.setVisited(attendance.getVisited());

        return dto;
    }

    private User findUser(Long id) {
        Optional<User> student = userRepository.getById(id);
        if (student.isEmpty())
            throw new IllegalArgumentException("User with id " + id + " not found");

        return student.get();
    }

    private Lesson findLesson(Long id) {
        Optional<Lesson> lesson = lessonRepository.getById(id);
        if (lesson.isEmpty())
            throw new IllegalArgumentException("Lesson with id " + id + " not found");

        return lesson.get();
    }
}
