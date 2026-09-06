package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.ChangePasswordReq;
import ca.vetClinic.api.dto.request.LoginReq;
import ca.vetClinic.api.dto.request.RegisterReq;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.application.command.ChangePasswordCmd;
import ca.vetClinic.application.command.LoginCmd;
import ca.vetClinic.application.command.RegisterCmd;
import ca.vetClinic.application.dto.AuthResult;
import ca.vetClinic.application.service.AuthServiceImpl;
import ca.vetClinic.domain.enumerator.Role;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class Authentification {

	private final AuthServiceImpl authService;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterReq request) {
		RegisterCmd cmd = new RegisterCmd(request.email(), request.password(), request.firstName(), request.lastName(),
				request.phoneNumber(), Role.USER);
		AuthResult result = authService.register(cmd);
		AuthResponse response = new AuthResponse(result.token(), result.tokenType(), result.expiresIn());
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginReq request) {
		LoginCmd cmd = new LoginCmd(request.email(), request.password(), Role.USER);
		AuthResult result = authService.login(cmd);
		AuthResponse response = new AuthResponse(result.token(), result.tokenType(), result.expiresIn());
		return ResponseEntity.ok(response);
	}
	@PatchMapping("/password")
	public ResponseEntity<Void> changePassword(@Valid @RequestBody ChangePasswordReq request) {
		ChangePasswordCmd cmd = new ChangePasswordCmd(request.email(), request.oldPassword(), request.newPassword());
		authService.changePassword(cmd);
		return ResponseEntity.noContent().build();
	}

}
