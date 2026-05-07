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
	private UUID id;
	private String firstName;
	private String lastName;
	private String phoneNumber;
	@CreationTimestamp
	private Instant userCreatedAt;
    @OneToOne
    @JoinColumn(name = "account_id")
    private AccountEntity account;
	public UserEntity(String firstName, String lastName, String phoneNumber) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.phoneNumber = phoneNumber;
	}

	public UserEntity() {

	}

}
