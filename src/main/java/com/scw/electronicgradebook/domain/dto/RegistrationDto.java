package com.scw.electronicgradebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class RegistrationDto {

    private UserDto userDto;

    @JsonProperty("password")
    private String password;
}
