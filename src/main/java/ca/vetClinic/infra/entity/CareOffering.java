package ca.vetClinic.infra.entity;

import ca.vetClinic.domain.enumerator.CareService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "careOfferings")
public class CareOffering {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	private String name;
	private String description;
	private double price;
	private int duration;
	@Enumerated(EnumType.STRING)
	private CareService careService;

	public CareOffering() {
	}
	public CareOffering(String name, String description, double price, int duration, CareService careService) {
		this.name = name;
		this.description = description;
		this.price = price;
		this.duration = duration;
		this.careService = careService;
	}

}
