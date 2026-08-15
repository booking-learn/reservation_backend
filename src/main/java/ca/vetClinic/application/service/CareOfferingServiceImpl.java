package ca.vetClinic.application.service;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.CareOffering;
import ca.vetClinic.domain.service.CareOfferingService;

import java.util.List;

public class CareOfferingServiceImpl implements CareOfferingService {
	@Override
	public List<CareOffering> findAllCareOfferings() {
		return List.of();
	}

	@Override
	public List<CareOffering> findByService(CareService careService) {
		return List.of();
	}
}
