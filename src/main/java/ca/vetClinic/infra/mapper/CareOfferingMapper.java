package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.CareOffering;
import ca.vetClinic.infra.entity.CareOfferingEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CareOfferingMapper {
	CareOffering toDomain(CareOfferingEntity careOfferingEntity);
	CareOfferingEntity toEntity(CareOffering careOffering);
}
