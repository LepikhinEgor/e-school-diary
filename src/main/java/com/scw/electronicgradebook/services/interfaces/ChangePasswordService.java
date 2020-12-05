package com.scw.electronicgradebook.services.interfaces;

import com.scw.electronicgradebook.domain.dto.ChangePasswordDto;
import com.scw.electronicgradebook.domain.dto.ResetPasswordDto;

public interface ChangePasswordService {

    void changePassword(ChangePasswordDto dto);

    void resetPassword(ResetPasswordDto dto);
}
