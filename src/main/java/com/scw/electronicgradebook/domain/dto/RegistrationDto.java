package com.scw.electronicgradebook.domain.dto;

import com.scw.electronicgradebook.domain.validators.Password;
import com.scw.electronicgradebook.domain.validators.Telephone;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

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

    @Length(max = 300)
    private String address;

    @Telephone
    private String phone;
}
