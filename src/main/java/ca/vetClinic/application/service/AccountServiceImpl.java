package ca.vetClinic.application.service;

import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.domain.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

	private AccountRepository accountRepository;
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
		accountRepository.save(account);
	}

	@Override
	public Account findByEmail(String email) {
		return accountRepository.findByEmail(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		return accountRepository.existsByEmail(email);
	}
}
