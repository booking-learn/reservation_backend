package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.UpdateEmailRequest;
import ca.vetClinic.api.dto.request.UpdatePasswordRequest;
import ca.vetClinic.api.dto.request.UpdateUserRequest;
import ca.vetClinic.application.command.UpdateEmailCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.application.command.UpdateUserCmd;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final AccountService accountService;

	@PutMapping
	public ResponseEntity<Void> update(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid UpdateUserRequest request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdateUserCmd cmd = new UpdateUserCmd(request.firstName(), request.lastName(), request.phoneNumber());
		userService.updateUser(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/email")
	public ResponseEntity<Void> updateEmail(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid UpdateEmailRequest request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdateEmailCmd cmd = new UpdateEmailCmd(request.oldEmail(), request.newEmail());
		accountService.updateEmail(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/password")
	public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid UpdatePasswordRequest request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdatePasswordCmd cmd = new UpdatePasswordCmd(request.oldPassword(), request.newPassword());
		accountService.updatePassword(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
}
