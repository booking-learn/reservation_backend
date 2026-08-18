package ca.vetClinic.unit.service;

import ca.vetClinic.application.service.CareOfferingServiceImpl;
import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.repository.CareOfferingRepository;
import ca.vetClinic.domain.service.CareOfferingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CareOfferingServiceTest {
	@Mock
	private CareOfferingRepository careOfferingRepository;

	private CareService careService;
	private CareOfferingService careOfferingService;

	@BeforeEach
	void setUp() {
		careOfferingService = new CareOfferingServiceImpl(careOfferingRepository);
		careService = CareService.STERILIZATION;
	}
	@Test
	void givenFindCareOfferingsWithoutFilter_thenFindAllCareOfferings() {
		careOfferingService.findCareOfferings(null);
		verify(careOfferingRepository, times(1)).findAll();
	}
	@Test
	void givenFindCareOfferingsWithoutFilter_thenDontFindAllCareOfferingsWithFilter() {
		careOfferingService.findCareOfferings(null);
		verify(careOfferingRepository, never()).findByService(any(CareService.class));
	}
	@Test
	void givenFindCareOfferingsWithFilter_thenFindAllCareOfferingsWithFilter() {
		careOfferingService.findCareOfferings(careService);
		verify(careOfferingRepository, times(1)).findByService(careService);
	}
	@Test
	void givenFindCareOfferingsWithFilter_thenDontFindAllCareOfferingsWithoutFilter() {
		careOfferingService.findCareOfferings(careService);
		verify(careOfferingRepository, never()).findAll();
	}
}