package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.CreateEmployeeReq;
import ca.vetClinic.api.dto.request.UpdateEmployeeReq;
import ca.vetClinic.api.dto.request.UpdatePasswordReq;
import ca.vetClinic.api.dto.response.EmployeeResponse;
import ca.vetClinic.application.command.UpdateEmployeeCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.EmployeeService;
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
@RequestMapping("/employee")
@RequiredArgsConstructor
public class Employee {
	private final EmployeeService employeeService;
	private final AccountService accountService;

	@PreAuthorize("hasRole('IT_ADMIN')")
	@PostMapping
	ResponseEntity<Void> createEmployee(@Valid @RequestBody CreateEmployeeReq request) {
		employeeService.createEmployee(request.firstName(), request.lastName(), request.phoneNumber(), request.role());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
	@GetMapping("/me")
	ResponseEntity<EmployeeResponse> getMe(@AuthenticationPrincipal UserDetails user) {
		Account account = accountService.findByEmail(user.getUsername());
		ca.vetClinic.domain.model.Employee specificEmployee = employeeService.findByAccountId(account.getId());
		EmployeeResponse response = new EmployeeResponse(specificEmployee.getFirstName(),
				specificEmployee.getLastName(), specificEmployee.getPhoneNumber());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PreAuthorize("hasRole('IT_ADMIN')")
	@GetMapping("/{id}")
	ResponseEntity<EmployeeResponse> getEmployeeById(@PathVariable UUID id) {
		ca.vetClinic.domain.model.Employee specificEmployee = employeeService.findById(id);
		EmployeeResponse response = new EmployeeResponse(specificEmployee.getFirstName(),
				specificEmployee.getLastName(), specificEmployee.getPhoneNumber());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PreAuthorize("hasRole('IT_ADMIN')")
	@GetMapping
	ResponseEntity<List<EmployeeResponse>> getAllEmployees() {
		List<ca.vetClinic.domain.model.Employee> employees = employeeService.findAll();
		List<EmployeeResponse> response = employees.stream()
				.map(employee -> new EmployeeResponse(employee.getFirstName(), employee.getLastName(),
						employee.getPhoneNumber()))
				.toList();
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	@PutMapping
	ResponseEntity<Void> updateEmployee(@AuthenticationPrincipal UserDetails user,
			@Valid @RequestBody UpdateEmployeeReq request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdateEmployeeCmd cmd = new UpdateEmployeeCmd(request.firstName(), request.lastName(), request.phoneNumber());
		employeeService.updateEmployee(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
	@PatchMapping("/password")
	ResponseEntity<Void> updatePassword(@AuthenticationPrincipal UserDetails user,
			@Valid @RequestBody UpdatePasswordReq request) {
		Account account = accountService.findByEmail(user.getUsername());
		UpdatePasswordCmd cmd = new UpdatePasswordCmd(request.oldPassword(), request.newPassword());
		accountService.updatePassword(account.getId(), cmd);
		return ResponseEntity.noContent().build();
	}
	@PreAuthorize("hasRole('IT_ADMIN')")
	@DeleteMapping("/{id}")
	ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
		employeeService.delete(id);
		return ResponseEntity.noContent().build();
	}
}
