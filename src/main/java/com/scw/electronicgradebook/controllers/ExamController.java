package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.ExamDto;
import com.scw.electronicgradebook.services.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @Secured("EXAM_READ")
    @GetMapping("/exam/{exam_id}")
    public ExamDto getExam(@PathVariable("exam_id") Long examId) {
        return examService.getById(examId).orElse(null);
    }

    @Secured("EXAM_WRITE")
    @PostMapping("/exam")
    public void createExam(@RequestBody ExamDto dto) {
        examService.create(dto);
    }

    @Secured("EXAM_WRITE")
    @PutMapping("/exam/{exam_id}")
    public void updateExam(@RequestBody ExamDto dto,
                           @PathVariable("exam_id") Long examId) {
        examService.update(dto, examId);
    }

    @Secured("EXAM_WRITE")
    @DeleteMapping("/exam/{exam_id}")
    public void deleteExam(@PathVariable("exam_id") Long examId) {
        examService.delete(examId);
    }
}
