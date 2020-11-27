package com.scw.electronicgradebook.domain.dto;

import com.scw.electronicgradebook.domain.validators.Password;
import lombok.Data;

@Data
public class ChangePasswordDto {

    @Password
    private String oldPassword;

    @Password
    private String newPassword;

    @Password
    private String newPasswordConfirm;
}
