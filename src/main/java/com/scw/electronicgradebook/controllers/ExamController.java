package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.ExamDto;
import com.scw.electronicgradebook.services.interfaces.ExamService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ExamController {

    private final ExamService examService;

    @PreAuthorize("hasAuthority('EXAM_READ')")
    @GetMapping("/exam/{exam_id}")
    public ExamDto getExam(@PathVariable("exam_id") Long examId) {
        return examService.getById(examId).orElse(null);
    }

    @PreAuthorize("hasAuthority('EXAM_WRITE')")
    @PostMapping("/exam")
    public void createExam(@RequestBody ExamDto dto) {
        examService.create(dto);
    }

    @PreAuthorize("hasAuthority('EXAM_WRITE')")
    @PutMapping("/exam/{exam_id}")
    public void updateExam(@RequestBody ExamDto dto,
                           @PathVariable("exam_id") Long examId) {
        examService.update(dto, examId);
    }

    @PreAuthorize("hasAuthority('EXAM_WRITE')")
    @DeleteMapping("/exam/{exam_id}")
    public void deleteExam(@PathVariable("exam_id") Long examId) {
        examService.delete(examId);
    }
}
