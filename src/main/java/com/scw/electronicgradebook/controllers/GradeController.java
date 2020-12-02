package com.scw.electronicgradebook.controllers;

import com.scw.electronicgradebook.domain.dto.GradeDto;
import com.scw.electronicgradebook.services.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    @PreAuthorize("hasAuthority('GRADE_READ')")
    @GetMapping("/grade/{grade_id}")
    public GradeDto getGrade(@PathVariable("grade_id") Long gradeId) {
        return gradeService.getById(gradeId).orElse(null);
    }

    @PreAuthorize("hasAuthority('GRADE_WRITE')")
    @PostMapping("/grade")
    public void createGrade(@RequestBody GradeDto gradeDto) {
        gradeService.create(gradeDto);
    }

    @PreAuthorize("hasAuthority('GRADE_WRITE')")
    @PutMapping("/grade/{grade_id}")
    public void updateGrade(@RequestBody GradeDto gradeDto,
                            @PathVariable("grade_id") Long gradeId) {
        gradeService.update(gradeDto, gradeId);
    }

    @PreAuthorize("hasAuthority('GRADE_WRITE')")
    @DeleteMapping("/grade/{grade_id}")
    public void deleteGrade(@PathVariable("grade_id") Long gradeId) {
        gradeService.delete(gradeId);
    }

    @Secured({"ROLE_TEACHER", "ROLE_ADMIN"})
    @GetMapping("/grades")
    public List<GradeDto> getGradesInInterval(@RequestParam("date_from") Long dateFrom,
                                              @RequestParam("date_to") Long dateTo) {
        return gradeService.getInTimeInterval(dateFrom, dateTo);
    }

    @DeleteMapping("/dev/grades/all")
    public void deleteAllGrades() {
        gradeService.deleteAll();
    }
}
