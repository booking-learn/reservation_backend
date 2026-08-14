package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.ChangePasswordReq;
import ca.vetClinic.api.dto.request.LoginReq;
import ca.vetClinic.api.dto.request.RegisterReq;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.application.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Authentification {

	private final AuthService authService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterReq request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginReq request) {
		return ResponseEntity.ok(authService.login(request));
	}
	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordReq request) {
		authService.changePassword(request);
		return ResponseEntity.noContent().build();
	}

}
