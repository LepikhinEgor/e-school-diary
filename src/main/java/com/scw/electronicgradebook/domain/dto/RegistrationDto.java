package com.scw.electronicgradebook.domain.dto;

import com.scw.electronicgradebook.domain.validators.Password;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class RegistrationDto {

    @NotEmpty
    private String name;

    @NotNull
    private Integer age;

    @NotEmpty
    private String login;

    @NotEmpty
    private String userType;

    @NotEmpty
    private String securityAnswer;

    @NotEmpty
    @Password
    private String password;

    @NotEmpty
    @Password
    private String passwordConfirm;

}
