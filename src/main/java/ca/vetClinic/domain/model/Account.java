package ca.vetClinic.domain.model;

import ca.vetClinic.domain.enumerator.Role;

import java.util.Objects;
import java.util.UUID;

public class Account {
	private UUID id;
	private String email;
	private String password;
	private Role role;

	public Account(UUID id, String email, String password, Role role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Role getRole() {
		return role;
	}
	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		Account account = (Account) o;
		return Objects.equals(id, account.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
