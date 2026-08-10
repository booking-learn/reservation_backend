package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.Employee;
import ca.vetClinic.infra.entity.EmployeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeMapper {

	@Mapping(target = "accountId", source = "account.id")
	Employee toDomain(EmployeEntity employeEntity);

	@Mapping(target = "account.id", source = "accountId")
	EmployeEntity toEntity(Employee employee);
}
