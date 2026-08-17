package ca.vetClinic.application.service;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.CareOffering;
import ca.vetClinic.domain.repository.CareOfferingRepository;
import ca.vetClinic.domain.service.CareOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CareOfferingServiceImpl implements CareOfferingService {
	private final CareOfferingRepository careOfferingRepository;
	@Override
	public List<CareOffering> findCareOfferings(CareService service) {
		List<CareOffering> careOfferings = (service != null)
				? careOfferingRepository.findByService(service)
				: careOfferingRepository.findAll();
		return careOfferings;
	}

}
