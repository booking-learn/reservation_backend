package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountRepository {

	List<Account> findAll();

	Account findById(UUID id);

	void deleteById(UUID id);

	void save(Account account);

	Account findByEmail(String email);

	boolean existsByEmail(String email);

	void update(Account account);
}
