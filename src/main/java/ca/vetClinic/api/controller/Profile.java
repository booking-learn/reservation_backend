package ca.vetClinic.api.controller;

import ca.vetClinic.api.mapper.EmployeeMapper;
import ca.vetClinic.api.mapper.UserMapper;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.EmployeeService;
import ca.vetClinic.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class Profile {
	private final AccountService accountService;
	private final UserService userService;
	private final EmployeeService employeeService;
	private final UserMapper userMapper;
	private final EmployeeMapper employeeMapper;
	@GetMapping
	public ResponseEntity<?> getProfile(@AuthenticationPrincipal UserDetails userDetails) {
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
