package com.faithjoyfundation.autopilotapi.v1.controllers.auth;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.auth.NewPasswordRequest;
import com.faithjoyfundation.autopilotapi.v1.services.NewPasswordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@Tag(name = "New Password", description = "API endpoints for changing current log user password, authentication required")
@RestController
@RequestMapping("/api/v1/new-password")
public class NewPasswordController {
    @Autowired
    private NewPasswordService newPasswordService;

    // @PreAuthorize("hasAnyRole('ADMIN', 'EMPLOYEE', 'MANAGER')")
    @Operation(summary = "Change current log user password", description = "Change current log user password, all users can access this endpoint")
    @PostMapping
    public ResponseEntity<?> changePassword(@Valid @RequestBody NewPasswordRequest newPasswordRequest, Principal principalUser) {
        newPasswordService.changePassword(principalUser, newPasswordRequest);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Your password has been changed successfully"));
    }
}
