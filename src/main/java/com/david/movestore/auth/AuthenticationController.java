package com.david.movestore.auth;

import java.io.IOException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.david.movestore.exceptions.UserExistsException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
  private final AuthenticationService service;

  @PostMapping("/signup")
  public ResponseEntity<AuthenticationResponse> register(
      @RequestBody RegisterRequest request) throws UserExistsException {
    return ResponseEntity.ok(service.register(request));
  }

  @PostMapping("/signin")
  public ResponseEntity<AuthenticationResponse> authenticate(
      @RequestBody AuthenticationRequest request) {
    return ResponseEntity.ok(service.authenticate(request));
  }

  @PostMapping("/refresh")
  public ResponseEntity<AuthenticationResponse> refreshToken(
      HttpServletRequest request,
      HttpServletResponse response) throws IOException {
    return ResponseEntity.ok(service.refreshToken(request, response));
  }
}
