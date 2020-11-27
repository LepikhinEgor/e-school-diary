package com.scw.electronicgradebook.domain.dto;

import com.scw.electronicgradebook.domain.validators.Password;
import lombok.Data;

@Data
public class RegistrationDto {

    private String name;

    private Integer age;

    private String login;

    private String userType;

    @Password
    private String password;

    @Password
    private String passwordConfirm;
}
