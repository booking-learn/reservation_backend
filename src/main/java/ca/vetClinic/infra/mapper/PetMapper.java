package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.Pet;
import ca.vetClinic.infra.entity.PetEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PetMapper {
	Pet toDomain(PetEntity petEntity);
	PetEntity toEntity(Pet pet);
}
