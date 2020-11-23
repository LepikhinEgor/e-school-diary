package com.scw.electronicgradebook.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserDto {

    @JsonProperty("name")
    private String name;

    @JsonProperty("login")
    private String login;

    @JsonProperty("user_type")
    private String userType;
}
