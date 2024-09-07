package com.faithjoyfundation.autopilotapi.v1.config.security.jwt;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

import static com.faithjoyfundation.autopilotapi.v1.config.security.jwt.JwtProperties.CHARACTER_ENCODING;
import static com.faithjoyfundation.autopilotapi.v1.config.security.jwt.JwtProperties.CONTENT_TYPE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(CONTENT_TYPE);
        response.setCharacterEncoding(CHARACTER_ENCODING);

        String message = "{\"error\": \"Unauthorized\", \"message\": \"" + authException.getMessage() + "\"}";
        response.getWriter().write(message);
    }
}