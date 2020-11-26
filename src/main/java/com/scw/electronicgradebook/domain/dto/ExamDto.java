package com.scw.electronicgradebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ExamDto {

    @JsonProperty("start_time")
    private Long startTime;

    @JsonProperty("end_time")
    private Long endTime;

    @JsonProperty("name")
    private String name;

    @JsonProperty("examiner_id")
    private Long examinerId;

    @JsonProperty("subject")
    private String subject;
}
