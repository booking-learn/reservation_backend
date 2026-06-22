package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.LoginRequest;
import ca.vetClinic.api.dto.request.RegisterRequest;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.application.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

	private final AuthService authService;

	/*
	 * @PostMapping("/register") public ResponseEntity<AuthResponse>
	 * register(@RequestBody RegisterRequest request) { return
	 * ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request))
	 * ; }
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
		log.info("Registration request for: {}", request.email());
		try {
			return ResponseEntity.ok(authService.register(request));
		} catch (Exception e) {
			log.error("Registration failed for: {}", request.email(), e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
		}
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}
}
