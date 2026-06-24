package ca.vetClinic.integration.repository;

import ca.vetClinic.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Rollback
@Transactional
public class AccountRepositoryImplTest extends AbstractAccountRepositoryTest {

	@Autowired
	private AccountRepository accountRepository;

	@Override
	protected AccountRepository createRepository() {
		return accountRepository;
	}
}