package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.LessonDto;
import com.scw.electronicgradebook.services.interfaces.LessonService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LessonController {

    private final LessonService lessonService;

    @PreAuthorize("hasAuthority('LESSON_READ')")
    @GetMapping("/lesson/{lesson_id}")
    public LessonDto getLesson(@PathVariable("lesson_id") Long lessonId) {
        return lessonService.getById(lessonId).orElse(null);
    }

    @PreAuthorize("hasAuthority('LESSON_WRITE')")
    @PostMapping("/lesson")
    public ResponseEntity<Object> createLesson(@RequestBody LessonDto lessonDto) {
        lessonService.create(lessonDto);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PreAuthorize("hasAuthority('LESSON_WRITE')")
    @PutMapping("/lesson/{lesson_id}")
    public void updateLesson(@RequestBody LessonDto lessonDto,
                             @PathVariable("lesson_id") Long lessonId) {
        lessonService.update(lessonDto, lessonId);
    }

    @PreAuthorize("hasAuthority('LESSON_WRITE')")
    @DeleteMapping("/lesson/{lesson_id}")
    public void deleteLesson(@PathVariable("lesson_id") Long lessonId) {
        lessonService.delete(lessonId);
    }
}
