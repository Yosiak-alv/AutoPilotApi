package com.faithjoyfundation.autopilotapi.v1.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String message;
    private String email;
    private String token;
}
