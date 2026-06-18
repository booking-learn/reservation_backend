package ca.vetClinic.infra.entity;

import ca.vetClinic.domain.enumerator.CareService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "care_offerings")
public class CareOfferingEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "price", nullable = false)
	private double price;

	@Column(name = "duration", nullable = false)
	private int duration;

	@Enumerated(EnumType.STRING)
	@Column(name = "care_service", nullable = false)
	private CareService careService;

	public CareOfferingEntity() {
	}

	public CareOfferingEntity(String name, String description, double price, int duration, CareService careService) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.careService = careService;
	}
}