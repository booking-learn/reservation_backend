package ca.vetClinic.infra.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.UUID;

@Setter
@Getter
@Entity
@Table(name = "users")
public class UserEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	@Column(name = "id", columnDefinition = "BINARY(16)", updatable = false, nullable = false)
	private UUID id;

	@Column(name = "first_name", nullable = false, length = 100)
	private String firstName;

	@Column(name = "last_name", nullable = false, length = 100)
	private String lastName;

	@Column(name = "phone_number", nullable = false, length = 15)
	private String phoneNumber;

	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private Instant userCreatedAt;

	@OneToOne
	@JoinColumn(name = "account_id", nullable = false, columnDefinition = "BINARY(16)")
	private AccountEntity account;

	public UserEntity() {
	}

	public UserEntity(String firstName, String lastName, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}
}
