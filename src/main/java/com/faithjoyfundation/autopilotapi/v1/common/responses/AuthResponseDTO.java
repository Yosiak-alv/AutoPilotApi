package com.faithjoyfundation.autopilotapi.v1.common.responses;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String message;
    private String email;
    private String token;
}
