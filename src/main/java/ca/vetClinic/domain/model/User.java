package ca.vetClinic.domain.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class User {
	private UUID id;
	private UUID accountId;
	private String firstName;
	private String lastName;

	private String phoneNumber;
	private Instant userCreatedAt;

	public User(UUID id, UUID accountId, String firstName, String lastName, String phoneNumber, Instant userCreatedAt) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
		this.userCreatedAt = userCreatedAt;
		this.id = id;
		this.accountId = accountId;
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

	public UUID getAccountId() {
		return accountId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null || getClass() != obj.getClass())
			return false;
		return this.getId().equals(((User) obj).getId());
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
