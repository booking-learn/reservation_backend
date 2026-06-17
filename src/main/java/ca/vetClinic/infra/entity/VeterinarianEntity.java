package ca.vetClinic.infra.entity;

import ca.vetClinic.domain.enumerator.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity
@DiscriminatorValue("VETERINARIAN")
public class VeterinarianEntity extends EmployeEntity {

	private String specialty;

	public VeterinarianEntity() {
	}

	public VeterinarianEntity(String firstName, String lastName, String email, String password, String phoneNumber,
			String specialty) {
		super(firstName, lastName, email, password, phoneNumber);
		this.specialty = specialty;
	}

}