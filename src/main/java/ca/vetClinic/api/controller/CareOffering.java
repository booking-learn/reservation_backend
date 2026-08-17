package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.response.CareResponse;
import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.service.CareOfferingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/care")
@RequiredArgsConstructor
public class CareOffering {
	private final CareOfferingService careOfferingService;

	@GetMapping()
	ResponseEntity<List<CareResponse>> findAllCareOfferings(@RequestParam(required = false) CareService service) {
		List<ca.vetClinic.domain.model.CareOffering> careOfferings = careOfferingService.findCareOfferings(service);
		List<CareResponse> careResponses = careOfferings.stream()
				.map(careOffering -> new CareResponse(careOffering.getName(), careOffering.getDescription(),
						careOffering.getPrice(), careOffering.getDuration(), careOffering.getCareService()))
				.toList();
		return ResponseEntity.status(HttpStatus.OK).body(careResponses);
	}

}
