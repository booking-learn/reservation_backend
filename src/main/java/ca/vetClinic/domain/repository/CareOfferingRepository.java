package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.CareOffering;

import java.util.List;

public interface CareOfferingRepository {
	List<CareOffering> findAll();

	List<CareOffering> findByService(CareService service);
}
