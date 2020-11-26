package com.scw.electronicgradebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AttendanceDto {

    @JsonProperty("student_id")
    private Long studentId;

    @JsonProperty("lesson_id")
    private Long lessonId;

    @JsonProperty("visited")
    private Boolean visited;
}
