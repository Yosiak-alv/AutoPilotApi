package com.faithjoyfundation.autopilotapi.v1.controllers.auth;

import com.faithjoyfundation.autopilotapi.v1.common.responses.ApiResponse;
import com.faithjoyfundation.autopilotapi.v1.persistence.dto.user.UserRequest;
import com.faithjoyfundation.autopilotapi.v1.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Tag(name = "Users", description = "API endpoints for user, Admins only")
@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get all users", description = "Get all users with pagination, filters and search, Admins only")
    @GetMapping
    public ResponseEntity<?> index(
            @Parameter(description = "Search term to filter by name, email, or roles names, Example: John Doe")
            @RequestParam(required = false, defaultValue = "") String search,

            @Parameter(description = "Page number for pagination, default 0")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(description = "Number of records per page default 10")
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAllBySearch(search, page, size));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Get a user by ID", description = "Get a user by ID, Admins only")
    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(
                new ApiResponse<>( HttpStatus.OK.value(), userService.findDTOById(id)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Create a new user", description = "Create a new user, Admins only")
    @PostMapping
    public ResponseEntity<?> store(@Valid @RequestBody UserRequest userRequest) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>( HttpStatus.CREATED.value(), "User Created Successfully", userService.create(userRequest)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Update a user", description = "Update a user by ID, Admins only")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody UserRequest userRequest, Principal userPrincipal) {
        return ResponseEntity.status(HttpStatus.OK)
                .body( new ApiResponse<>(HttpStatus.OK.value(), "User Updated Successfully", userService.update(id, userRequest, userPrincipal)));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Reset a user's password", description = "Reset a user's password, generate a new password and send it to the user's email, Admins only")
    @PatchMapping("/{id}/reset-password")
    public ResponseEntity<?> resetPassword(@PathVariable Long id) {
        boolean reset = userService.resetTempPassword(id);
        return reset ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "Password reset successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Password could not be reset"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Delete a user", description = "Delete a user by ID, Admins only")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, Principal userPrincipal) {
        boolean deleted = userService.delete(id, userPrincipal);
        return deleted ? ResponseEntity.status(HttpStatus.OK)
                .body(new ApiResponse<>(HttpStatus.OK.value(), "User deleted successfully")) :
                ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "User could not be deleted"));
    }
}
