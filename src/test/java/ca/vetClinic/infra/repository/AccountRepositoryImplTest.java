package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.repository.AbstractAccountRepositoryTest;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.infra.mapper.AccountMapper;
import ca.vetClinic.infra.repository.jpa.AccountJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureTestDatabase;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AccountRepositoryImplTest extends AbstractAccountRepositoryTest {

	@Container
	@ServiceConnection
	static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8");

	@Autowired
	private AccountJpaRepository jpaRepository;
	@Autowired
	private AccountMapper mapper;

	@Override
	protected AccountRepository createRepository() {
		return new AccountRepositoryImpl(jpaRepository, mapper);
	}

	@AfterEach
	void cleanUp() {
		jpaRepository.deleteAll();
	}
}