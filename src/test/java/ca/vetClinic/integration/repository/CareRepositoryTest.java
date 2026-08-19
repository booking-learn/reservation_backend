package ca.vetClinic.integration.repository;

import ca.vetClinic.base.AbstractContainerBase;
import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.CareOffering;
import ca.vetClinic.domain.repository.CareOfferingRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CareRepositoryTest extends AbstractContainerBase {

	@Autowired
	private CareOfferingRepository careOfferingRepository;

	@Test
	void givenFindAll_thenReturnSixServices() {
		List<CareOffering> cares = careOfferingRepository.findAll();
		assertEquals(6, cares.size());
	}
	@Test
	void givenFindAll_thenReturnNotNullList() {
		List<CareOffering> cares = careOfferingRepository.findAll();
		assertNotNull(cares);
	}
	@Test
	void givenFindBySTERILIZATION_thenReturnNotNullList() {
		List<CareOffering> cares = careOfferingRepository.findByService(CareService.STERILIZATION);
		assertNotNull(cares);
	}
	@Test
	void givenFindByDENTISTRY_thenReturnNotNullList() {
		List<CareOffering> cares = careOfferingRepository.findByService(CareService.DENTISTRY);
		assertNotNull(cares);
	}
	@Test
	void givenFindByCONSULTATION_thenReturnNotNullList() {
		List<CareOffering> cares = careOfferingRepository.findByService(CareService.CONSULTATION);
		assertNotNull(cares);
	}
	@Test
	void givenFindByMEDICAL_IMAGING_thenReturnNotNullList() {
		List<CareOffering> cares = careOfferingRepository.findByService(CareService.MEDICAL_IMAGING);
		assertNotNull(cares);
	}
	@Test
	void givenFindByVACCINATION_thenReturnNotNullList() {
		List<CareOffering> cares = careOfferingRepository.findByService(CareService.VACCINATION);
		assertNotNull(cares);
	}
}