package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.User;
import ca.vetClinic.infra.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

	@Mapping(target = "accountId", source = "account.id")
	User toDomain(UserEntity entity);
	@Mapping(target = "account", ignore = true)
	UserEntity toEntity(User user);
}