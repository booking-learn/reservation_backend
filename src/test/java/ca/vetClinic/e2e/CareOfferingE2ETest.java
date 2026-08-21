package ca.vetClinic.e2e;

import ca.vetClinic.domain.enumerator.CareService;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CareOfferingE2ETest extends BaseE2ETest {

	@Test
	void findAllCareOfferings_returnsOk() throws Exception {
		mockMvc.perform(get("/cares")).andExpect(status().isOk());
	}

	@Test
	void findAllCareOfferings_returnsAllOfferings() throws Exception {
		mockMvc.perform(get("/cares")).andExpect(jsonPath("$.length()").value(6));
	}

	@Test
	void findCareOfferingsByService_returnsOnlyMatchingCount() throws Exception {
		mockMvc.perform(get("/cares").param("service", CareService.STERILIZATION.name()))
				.andExpect(jsonPath("$.length()").value(2));
	}

	@Test
	void findCareOfferingsByService_returnsOnlySterilizationCareService() throws Exception {
		mockMvc.perform(get("/cares").param("service", CareService.STERILIZATION.name()))
				.andExpect(jsonPath("$[*].careService", everyItem(is(CareService.STERILIZATION.name()))));
	}

	@Test
	void findCareOfferingsByService_returnsOnlyVaccinationCareService() throws Exception {
		mockMvc.perform(get("/cares").param("service", CareService.VACCINATION.name()))
				.andExpect(jsonPath("$[*].careService", everyItem(is(CareService.VACCINATION.name()))));
	}

	@Test
	void findCareOfferingsByService_unknownServiceReturnsBadRequest() throws Exception {
		mockMvc.perform(get("/cares").param("service", "INVALID")).andExpect(status().isBadRequest());
	}
}