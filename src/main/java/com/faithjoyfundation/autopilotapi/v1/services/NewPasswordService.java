package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.auth_managment.NewPasswordRequest;

import java.security.Principal;

public interface NewPasswordService {
    void changePassword(Principal principalUser, NewPasswordRequest newPasswordRequest);
}
