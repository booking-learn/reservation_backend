package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.infra.entity.EmployeEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeMapper {
	Employee toDomain(EmployeEntity employeEntity);
	EmployeEntity toEntity(Employee employee);
}
