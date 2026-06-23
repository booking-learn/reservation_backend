package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.LoginRequest;
import ca.vetClinic.api.dto.request.RegisterRequest;
import ca.vetClinic.api.dto.response.AuthResponse;
import ca.vetClinic.api.mapper.EmployeeMapper;
import ca.vetClinic.api.mapper.UserMapper;
import ca.vetClinic.application.service.AuthService;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.EmployeeService;
import ca.vetClinic.domain.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;
	private final AccountService accountService;
	private final UserService userService;
	private final EmployeeService employeeService;
	private final UserMapper userMapper;
	private final EmployeeMapper employeeMapper;

	@PostMapping("/register")
	public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
		return ResponseEntity.status(HttpStatus.CREATED).body(authService.register(request));
	}

	@PostMapping("/login")
	public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
		return ResponseEntity.ok(authService.login(request));
	}
	@GetMapping("/me")
	public ResponseEntity<?> getMe(@AuthenticationPrincipal UserDetails userDetails) {
		Account account = accountService.findByEmail(userDetails.getUsername());

		if (account.getRole() == Role.USER) {
			User user = userService.findByAccountId(account.getId());
			return ResponseEntity.ok(userMapper.toDto(user, account));
		} else {
			Employee employee = employeeService.findByAccountId(account.getId());
			return ResponseEntity.ok(employeeMapper.toDto(employee, account));
		}
	}
}
