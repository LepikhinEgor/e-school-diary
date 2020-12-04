package com.scw.electronicgradebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class SensitiveDataDto {

    @JsonProperty("address")
    private String address;

    @JsonProperty("phone")
    private String phone;
}
