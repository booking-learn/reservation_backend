package ca.vetClinic.infra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "pets")
public class PetEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private UUID ownerId;
	private String name;
	private String species;
	private String breed;
	private String gender;
	private LocalDate birthDate;

	public PetEntity() {
	}
	public PetEntity(UUID ownerId, String name, String species, String breed, String gender, LocalDate birthDate) {
		this.ownerId = ownerId;
		this.name = name;
		this.species = species;
		this.breed = breed;
		this.gender = gender;
		this.birthDate = birthDate;
	}

}
