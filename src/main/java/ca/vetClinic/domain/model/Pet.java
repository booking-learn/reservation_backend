package ca.vetClinic.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public class Pet {
	private UUID id;
	private UUID ownerId;
	private String name;
	private String species;
	private String breed;
	private String gender;
	private LocalDate birthDate;

	public Pet() {
	}

	public Pet(UUID id, UUID ownerId, String name, String species, String breed, String gender, LocalDate birthDate) {
		this.id = id;
		this.ownerId = ownerId;
		this.name = name;
		this.species = species;
		this.breed = breed;
		this.gender = gender;
		this.birthDate = birthDate;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public UUID getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(UUID ownerId) {
		this.ownerId = ownerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		return this.getId().equals(((Pet) o).getId());
	}
	@Override
	public int hashCode() {
		return this.id != null ? this.id.hashCode() : 0;
	}
}
