package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.Pet;
import ca.vetClinic.infra.entity.PetEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PetMapper {
	@Mapping(target = "ownerId", source = "user.id")
	Pet toDomain(PetEntity petEntity);
	@Mapping(target = "user.id", source = "ownerId")
	PetEntity toEntity(Pet pet);
}
