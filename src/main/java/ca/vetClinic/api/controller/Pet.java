package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.PetReq;
import ca.vetClinic.api.dto.response.PetResponse;
import ca.vetClinic.application.command.UpdatePetCmd;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.PetService;
import ca.vetClinic.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class Pet {

	private final PetService petService;
	private final AccountService accountService;
	private final UserService userService;

	@PostMapping("/pets")
	public ResponseEntity<Void> createPet(@AuthenticationPrincipal UserDetails user, @RequestBody PetReq request) {
		Account account = accountService.findByEmail(user.getUsername());
		User specificUser = userService.findByAccountId(account.getId());
		ca.vetClinic.domain.model.Pet pet = petService.createPet(specificUser.getId(), request.name(),
				request.species(), request.breed(), request.gender(), request.birthDate());
		petService.save(pet);
		return ResponseEntity.ok().build();
	}
	@PreAuthorize("hasAnyRole('VET','VET_TECH','IT_ADMIN')")
	@GetMapping("/pets/{petId}")
	public ResponseEntity<PetResponse> getPetById(@PathVariable UUID petId) {
		ca.vetClinic.domain.model.Pet pet = petService.findByPetId(petId);
		PetResponse response = new PetResponse(pet.getName(), pet.getSpecies(), pet.getBreed(), pet.getGender(),
				pet.getBirthDate());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/pets")
	public ResponseEntity<List<PetResponse>> getPetsForCurrentUser(@AuthenticationPrincipal UserDetails user) {
		Account account = accountService.findByEmail(user.getUsername());
		User specificUser = userService.findByAccountId(account.getId());
		List<ca.vetClinic.domain.model.Pet> pets = petService.findAllByOwnerId(specificUser.getId());
		List<PetResponse> petResponseList = pets.stream().map(pet -> new PetResponse(pet.getName(), pet.getSpecies(),
				pet.getBreed(), pet.getGender(), pet.getBirthDate())).toList();
		return ResponseEntity.status(HttpStatus.OK).body(petResponseList);
	}

	@PatchMapping("/pets/{petId}")
	@PreAuthorize("hasAnyRole('VET','VET_TECH','IT_ADMIN')")
	public ResponseEntity<Void> updatePetAsStaff(@PathVariable UUID petId, @RequestBody UpdatePetCmd cmd) {
		petService.update(petId, cmd);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/pets/{petId}/owner")
	public ResponseEntity<Void> updatePetAsOwner(@AuthenticationPrincipal UserDetails user, @PathVariable UUID petId,
			@RequestBody UpdatePetCmd cmd) {
		petService.update(petId, cmd);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/pets/{petId}")
	public ResponseEntity<Void> deletePet(@AuthenticationPrincipal UserDetails user, @PathVariable UUID petId) {
		petService.deleteById(petId);
		return ResponseEntity.ok().build();
	}
}