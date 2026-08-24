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
	@Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_id", nullable = false, columnDefinition = "BINARY(16)")
	private UserEntity user;
	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "species", nullable = false, length = 100)
	private String species;

	@Column(name = "breed", length = 100)
	private String breed;

	@Column(name = "gender", length = 20)
	private String gender;

	@Column(name = "birth_date", nullable = false)
	private LocalDate birthDate;

	public PetEntity() {
	}

	public PetEntity(String name, String species, String breed, String gender, LocalDate birthDate) {
		this.name = name;
		this.species = species;
		this.breed = breed;
		this.gender = gender;
		this.birthDate = birthDate;
	}
}