package ca.vetClinic.domain.service;

import ca.vetClinic.application.command.UpdateEmailCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.domain.model.Account;

import java.util.List;
import java.util.UUID;

public interface AccountService {
	List<Account> findAll();

	Account findById(UUID id);

	void deleteById(UUID id);

	void save(Account account);

	Account findByEmail(String email);

	boolean existsByEmail(String email);

	void updateEmail(UUID id, UpdateEmailCmd cmd);

	void updatePassword(UUID id, UpdatePasswordCmd cmd);

	boolean isMustChangePassword(String email);
}
