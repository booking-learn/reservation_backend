package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.UserDto;
import ca.vetClinic.api.dto.request.UpdateUserRequest;
import ca.vetClinic.application.command.UpdateUserCommand;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
	private final UserService userService;
	private final AccountService accountService;

	@PutMapping
	public ResponseEntity<UserDto> update(@AuthenticationPrincipal UserDetails user,
			@RequestBody @Valid UpdateUserRequest request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdateUserCommand cmd = new UpdateUserCommand(request.firstName(), request.lastName(), request.phoneNumber());
		userService.updateUser(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
}
