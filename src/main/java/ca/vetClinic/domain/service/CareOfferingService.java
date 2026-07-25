package ca.vetClinic.domain.service;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.CareOffering;

import java.util.List;
import java.util.UUID;

public interface CareOfferingService {
	List<String> findAllCareOfferingsNames();

	List<CareOffering> findAllCareOfferings();

	List<CareOffering> findByCareService(CareService careService);

	CareOffering findById(UUID id);

}
