package ca.vetClinic.api.mapper;

import ca.vetClinic.api.dto.EmployeeDto;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Employee;
import org.springframework.stereotype.Component;

@Component
public class EmployeeMapper {

	public EmployeeDto toDto(Employee employee, Account account) {
		return new EmployeeDto(employee.getId(), account.getEmail(), employee.getFirstName(), employee.getLastName(),
				account.getRole());
	}
}
