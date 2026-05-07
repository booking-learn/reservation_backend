package ca.vetClinic.infra.repository.jpa;

import ca.vetClinic.infra.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountJpaRepository extends JpaRepository<AccountEntity, UUID> {
	Optional<AccountEntity> findByEmail(String email);
}
