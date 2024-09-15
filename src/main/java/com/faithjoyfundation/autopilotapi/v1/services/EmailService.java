package com.faithjoyfundation.autopilotapi.v1.services;

public interface EmailService {
    void sendEmail(String email, String subject, String message);
}
