package ca.vetClinic.infra.entity;

import ca.vetClinic.domain.model.Role;
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
    private UUID id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role;
    @OneToOne(mappedBy = "employees",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
    private EmployeEntity employee;
    @OneToOne(mappedBy = "users",cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
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
