package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.LessonDto;
import com.scw.electronicgradebook.services.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @Secured("LESSON_READ")
    @GetMapping("/lesson/{lesson_id}")
    public LessonDto getLesson(@PathVariable("lesson_id") Long lessonId) {
        return lessonService.getById(lessonId).orElse(null);
    }

    @Secured("LESSON_WRITE")
    @PostMapping("/lesson")
    public void createLesson(@RequestBody LessonDto lessonDto) {
        lessonService.create(lessonDto);
    }

    @Secured("LESSON_WRITE")
    @PutMapping("/lesson/{lesson_id}")
    public void updateLesson(@RequestBody LessonDto lessonDto,
                             @PathVariable("lesson_id") Long lessonId) {
        lessonService.update(lessonDto, lessonId);
    }

    @Secured("LESSON_WRITE")
    @DeleteMapping("/lesson/{lesson_id}")
    public void deleteLesson(@PathVariable("lesson_id") Long lessonId) {
        lessonService.delete(lessonId);
    }
}
