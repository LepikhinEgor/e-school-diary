package com.scw.electronicgradebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class GradeDto {

    @JsonProperty("exam_id")
    private Long examId;

    @JsonProperty("value")
    private Integer value;

    @JsonProperty("student_id")
    private Long studentId;
}
