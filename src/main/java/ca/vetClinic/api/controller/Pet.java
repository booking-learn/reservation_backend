package ca.vetClinic.api.controller;

import ca.vetClinic.api.dto.request.PetReq;
import ca.vetClinic.api.dto.response.PetResponse;
import ca.vetClinic.application.command.UpdatePetCmd;
import ca.vetClinic.domain.service.PetService;
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
@RequestMapping("/pets")
public class Pet {

	private final PetService petService;

	@PostMapping
	public ResponseEntity<Void> createPet(@AuthenticationPrincipal UserDetails user, @RequestBody PetReq request) {
		java.util.UUID userId = petService.findOwnerId(user.getUsername());
		ca.vetClinic.domain.model.Pet pet = petService.createPet(userId, request.name(), request.species(),
				request.breed(), request.gender(), request.birthDate());
		petService.save(pet);
		return ResponseEntity.ok().build();
	}
	@PreAuthorize("hasAnyRole('VET','VET_TECH','IT_ADMIN')")
	@GetMapping("/{petId}")
	public ResponseEntity<PetResponse> getPetById(@PathVariable UUID petId) {
		ca.vetClinic.domain.model.Pet pet = petService.findByPetId(petId);
		PetResponse response = new PetResponse(pet.getName(), pet.getSpecies(), pet.getBreed(), pet.getGender(),
				pet.getBirthDate());
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/mine")
	public ResponseEntity<List<PetResponse>> getPetsForCurrentUser(@AuthenticationPrincipal UserDetails user) {
		List<ca.vetClinic.domain.model.Pet> pets = petService.findAllByOwnerEmail(user.getUsername());
		List<PetResponse> petResponseList = pets.stream().map(pet -> new PetResponse(pet.getName(), pet.getSpecies(),
				pet.getBreed(), pet.getGender(), pet.getBirthDate())).toList();
		return ResponseEntity.status(HttpStatus.OK).body(petResponseList);
	}
	@PreAuthorize("hasAnyRole('VET','IT_ADMIN')")
	@GetMapping
	public ResponseEntity<List<PetResponse>> getAllPets() {
		List<ca.vetClinic.domain.model.Pet> pets = petService.findAll();
		List<PetResponse> petResponseList = pets.stream().map(pet -> new PetResponse(pet.getName(), pet.getSpecies(),
				pet.getBreed(), pet.getGender(), pet.getBirthDate())).toList();
		return ResponseEntity.status(HttpStatus.OK).body(petResponseList);
	}
	@PreAuthorize("hasAnyRole('VET','VET_TECH','IT_ADMIN')")
	@GetMapping("/email")
	public ResponseEntity<List<PetResponse>> getPetsForSpecificUser(@PathVariable String email) {
		List<ca.vetClinic.domain.model.Pet> pets = petService.findAllByOwnerEmail(email);
		List<PetResponse> petResponseList = pets.stream().map(pet -> new PetResponse(pet.getName(), pet.getSpecies(),
				pet.getBreed(), pet.getGender(), pet.getBirthDate())).toList();
		return ResponseEntity.status(HttpStatus.OK).body(petResponseList);
	}

	@PatchMapping("/{petId}")
	@PreAuthorize("hasAnyRole('VET','VET_TECH','IT_ADMIN')")
	public ResponseEntity<Void> updatePetAsStaff(@PathVariable UUID petId, @RequestBody UpdatePetCmd cmd) {
		petService.update(petId, cmd);
		return ResponseEntity.ok().build();
	}

	@PatchMapping("/{petId}/owner")
	public ResponseEntity<Void> updatePetAsOwner(@PathVariable UUID petId, @RequestBody UpdatePetCmd cmd) {
		petService.update(petId, cmd);
		return ResponseEntity.ok().build();
	}
	@PreAuthorize("hasAnyRole('IT_ADMIN')")
	@DeleteMapping("/{petId}")
	public ResponseEntity<Void> deletePetById(@PathVariable UUID petId) {
		petService.deleteById(petId);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping
	public ResponseEntity<Void> deletePet(@AuthenticationPrincipal UserDetails user) {
		petService.deleteByOwnerEmail(user.getUsername());
		return ResponseEntity.ok().build();
	}
}