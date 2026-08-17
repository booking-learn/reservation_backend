package ca.vetClinic.domain.service;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.CareOffering;

import java.util.List;

public interface CareOfferingService {

	List<CareOffering> findCareOfferings(CareService service);

}
