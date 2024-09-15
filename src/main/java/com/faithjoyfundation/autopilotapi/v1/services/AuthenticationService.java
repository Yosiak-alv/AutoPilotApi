package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.common.responses.AuthResponseDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.auth_managment.LoginRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.auth_managment.RegisterRequest;

import java.util.Map;

public interface AuthenticationService {
    AuthResponseDTO login(LoginRequest request);
}
