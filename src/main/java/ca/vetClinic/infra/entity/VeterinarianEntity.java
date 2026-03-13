package ca.vetClinic.infra.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "veterinarian")
public class VeterinarianEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String firstName;
	private String lastName;
	private String email;
	private String specialty;
	public VeterinarianEntity() {
	}
	public VeterinarianEntity(String firstName, String lastName, String email, String specialty) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.specialty = specialty;
	}

	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSpecialty() {
		return specialty;
	}
	public void setSpecialty(String specialty) {
		this.specialty = specialty;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
	}
}
