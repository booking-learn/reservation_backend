package ca.vetClinic.domain.model;

import java.time.Instant;
import java.util.UUID;

public class User {
	private UUID id;
	private String firstName;
	private String lastName;

	private String phoneNumber;
	private Instant userCreatedAt;

	public User(UUID id, String firstName, String lastName, String phoneNumber,
			Instant userCreatedAt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.userCreatedAt = userCreatedAt;
		this.id = id;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public Instant getUserCreatedAt() {
		return userCreatedAt;
	}

	public void setUserCreatedAt(Instant userCreatedAt) {
		this.userCreatedAt = userCreatedAt;
	}
}
