package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.responses.AuthResponseDTO;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.auth.LoginRequest;

public interface AuthenticationService {
    AuthResponseDTO login(LoginRequest request);
}
