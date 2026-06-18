package ca.vetClinic.infra.entity;

import ca.vetClinic.domain.enumerator.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "accounts")
public class AccountEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "email", nullable = false, unique = true)
	private String email;

	@Column(name = "password", nullable = false)
	private String password;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false, columnDefinition = "ENUM('VETERINARIAN','VET_TECH','RECEPTIONIST','IT_ADMIN')")
	private Role role;

	@OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private EmployeEntity employee;

	@OneToOne(mappedBy = "account", cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private UserEntity user;

	public AccountEntity() {
	}

	public AccountEntity(UUID id, String email, String password, Role role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.role = role;
	}
}
