package com.scw.electronicgradebook.domain.dto;

import com.scw.electronicgradebook.domain.validators.Password;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
public class ResetPasswordDto {

    @NotEmpty
    private String securityAnswer;

    @NotEmpty
    @Password
    private String newPassword;

    @NotEmpty
    @Password
    private String newPasswordConfirm;
}
