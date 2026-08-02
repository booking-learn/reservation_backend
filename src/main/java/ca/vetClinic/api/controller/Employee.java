package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.CreateEmployeeRequest;
import ca.vetClinic.domain.service.EmployeeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/employee")
@RequiredArgsConstructor
public class Employee {
	private final EmployeeService employeeService;

	@PostMapping
	ResponseEntity<Void> createEmployee(@Valid @RequestBody CreateEmployeeRequest request) {
		employeeService.createEmployee(request.firstName(), request.lastName(), request.phoneNumber(), request.role());
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}
}
