package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.UpdateEmailReq;
import ca.vetClinic.api.dto.request.UpdatePasswordReq;
import ca.vetClinic.api.dto.request.UpdateUserReq;
import ca.vetClinic.api.dto.response.UserResponse;
import ca.vetClinic.application.command.UpdateEmailCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.application.command.UpdateUserCmd;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class User {
	private final UserService userService;
	private final AccountService accountService;

	@PutMapping
	public ResponseEntity<Void> update(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid UpdateUserReq request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdateUserCmd cmd = new UpdateUserCmd(request.firstName(), request.lastName(), request.phoneNumber());
		userService.updateUser(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/email")
	public ResponseEntity<Void> updateEmail(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid UpdateEmailReq request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdateEmailCmd cmd = new UpdateEmailCmd(request.oldEmail(), request.newEmail());
		accountService.updateEmail(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/password")
	public ResponseEntity<Void> updatePassword(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid UpdatePasswordReq request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdatePasswordCmd cmd = new UpdatePasswordCmd(request.oldPassword(), request.newPassword());
		accountService.updatePassword(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
	@GetMapping("/me")
	public ResponseEntity<UserResponse> getMe(@AuthenticationPrincipal UserDetails user) {
		Account account = accountService.findByEmail(user.getUsername());
		ca.vetClinic.domain.model.User specificUser = userService.findByAccountId(account.getId());
		UserResponse response = new UserResponse(specificUser.getFirstName(), specificUser.getLastName(),
				specificUser.getPhoneNumber());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PreAuthorize("hasRole('IT_ADMIN')")
	@GetMapping("/{id}")
	public ResponseEntity<UserResponse> getUser(@PathVariable UUID id) {
		ca.vetClinic.domain.model.User specificUser = userService.findById(id);
		UserResponse response = new UserResponse(specificUser.getFirstName(), specificUser.getLastName(),
				specificUser.getPhoneNumber());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PreAuthorize("hasRole('IT_ADMIN')")
	@GetMapping
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		List<ca.vetClinic.domain.model.User> users = userService.findAll();
		List<UserResponse> response = users.stream()
				.map(user -> new UserResponse(user.getFirstName(), user.getLastName(), user.getPhoneNumber())).toList();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@DeleteMapping("/me")
	public ResponseEntity<Void> deleteUser(@AuthenticationPrincipal UserDetails user) {
		Account account = accountService.findByEmail(user.getUsername());
		ca.vetClinic.domain.model.User specificUser = userService.findByAccountId(account.getId());
		userService.delete(specificUser.getId());
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasRole('IT_ADMIN')")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUserById(@PathVariable UUID id) {
		userService.delete(id);
		return ResponseEntity.noContent().build();
	}

}
