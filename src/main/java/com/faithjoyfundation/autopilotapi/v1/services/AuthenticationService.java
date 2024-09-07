package com.faithjoyfundation.autopilotapi.v1.services;

import com.faithjoyfundation.autopilotapi.v1.dto.AuthResponseDTO;
import com.faithjoyfundation.autopilotapi.v1.dto.requests.LoginRequest;
import com.faithjoyfundation.autopilotapi.v1.dto.requests.RegisterRequest;

import java.util.Map;

public interface AuthenticationService {
    AuthResponseDTO login(LoginRequest request);
    Map<Object, Object>  register(RegisterRequest request);
}
