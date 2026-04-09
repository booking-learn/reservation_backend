package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.User;
import ca.vetClinic.infra.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
	User toDomain(UserEntity entity);
	UserEntity toEntity(User user);
}
