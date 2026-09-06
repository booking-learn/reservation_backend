package ca.vetClinic.application.service;

import ca.vetClinic.application.command.UpdateEmailCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private final AccountRepository accountRepository;
	private final PasswordEncoder passwordEncoder;

	private void validateNotNull(String variable, String name) {
		if (variable == null) {
			throw new IllegalArgumentException(name + "is null");
		}
	}
	@Override
	public List<Account> findAll() {
		return accountRepository.findAll();
	}

	@Override
	public Account findById(UUID id) {
		return accountRepository.findById(id);
	}

	@Override
	public void deleteById(UUID id) {
		accountRepository.deleteById(id);
	}

	@Override
	public void save(Account account) {
		if (account == null) {
			throw new IllegalArgumentException("Account is null");
		}
		accountRepository.save(account);
	}

	@Override
	public Account findByEmail(String email) {
		validateNotNull(email, "email");
		return accountRepository.findByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		validateNotNull(email, "email");
		return accountRepository.existsByEmail(email);
	}

	@Override
	public void updateEmail(UUID id, UpdateEmailCmd cmd) {
		Account account = accountRepository.findById(id);
		if (!Objects.equals(account.getEmail(), cmd.oldEmail())) {
			throw new BadCredentialsException("Invalid email!");
		}
		account.setEmail(cmd.newEmail());
		accountRepository.save(account);
	}

	@Override
	public void updatePassword(UUID id, UpdatePasswordCmd cmd) {
		Account account = accountRepository.findById(id);
		if (!passwordEncoder.matches(cmd.oldPassword(), account.getPassword())) {
			throw new BadCredentialsException("Invalid password!");
		}
		account.setPassword(passwordEncoder.encode(cmd.newPassword()));
		account.setMustChangePassword(false);
		accountRepository.save(account);
	}

	@Override
	public boolean isMustChangePassword(String email) {
		validateNotNull(email, "email");
		Account account = accountRepository.findByEmail(email);
		return account.isMustChangePassword();
	}
}
