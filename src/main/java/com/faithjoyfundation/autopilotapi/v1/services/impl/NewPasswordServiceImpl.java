package com.faithjoyfundation.autopilotapi.v1.services.impl;

import com.faithjoyfundation.autopilotapi.v1.persistence.dto.auth.NewPasswordRequest;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.BadRequestException;
import com.faithjoyfundation.autopilotapi.v1.exceptions.errors.ResourceNotFoundException;
import com.faithjoyfundation.autopilotapi.v1.persistence.models.auth.User;
import com.faithjoyfundation.autopilotapi.v1.persistence.repositories.UserRepository;
import com.faithjoyfundation.autopilotapi.v1.services.NewPasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class NewPasswordServiceImpl implements NewPasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void changePassword(Principal principalUser, NewPasswordRequest newPasswordRequest) {
        User user = userRepository
                .findByEmail(principalUser.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email " + principalUser.getName()));

        if (!passwordEncoder.matches(newPasswordRequest.getOldPassword(), user.getPassword())) {
            throw new BadRequestException("Old password is incorrect, please try again");
        }

        user.setPassword(passwordEncoder.encode(newPasswordRequest.getNewPassword()));
        userRepository.save(user);
    }
}
