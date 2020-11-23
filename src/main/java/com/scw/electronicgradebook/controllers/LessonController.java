package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.LessonDto;
import com.scw.electronicgradebook.services.LessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class LessonController {

    private LessonService lessonService;

    @Autowired
    public void setLessonService(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @GetMapping("/lesson/{lesson_id}")
    public LessonDto updateLesson(@PathVariable("lesson_id") Long lessonId) {
        return lessonService.getById(lessonId).orElse(null);
    }

    @PostMapping("/lesson")
    public void createLesson(@RequestBody LessonDto lessonDto) {
        lessonService.create(lessonDto);
    }

    @PutMapping("/lesson/{lesson_id}")
    public void updateLesson(@RequestBody LessonDto lessonDto,
                             @PathVariable("lesson_id") Long lessonId) {
        lessonService.update(lessonDto, lessonId);
    }
}
