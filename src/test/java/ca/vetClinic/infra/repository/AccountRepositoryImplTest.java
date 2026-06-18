package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
@Rollback
@Transactional
public class AccountRepositoryImplTest extends AbstractAccountRepositoryTest {

	@Container
	static MySQLContainer<?> mysql = new MySQLContainer<>("mysql:8").withDatabaseName("vet_clinic_db")
			.withUsername("root").withPassword("password");

	@DynamicPropertySource
	static void configureProperties(DynamicPropertyRegistry registry) {
		registry.add("spring.datasource.url", mysql::getJdbcUrl);
		registry.add("spring.datasource.username", mysql::getUsername);
		registry.add("spring.datasource.password", mysql::getPassword);
		registry.add("spring.datasource.driver-class-name", () -> "com.mysql.cj.jdbc.Driver");
		registry.add("spring.flyway.url", mysql::getJdbcUrl);
		registry.add("spring.flyway.user", mysql::getUsername);
		registry.add("spring.flyway.password", mysql::getPassword);
		registry.add("spring.flyway.enabled", () -> "true");
		registry.add("spring.jpa.hibernate.ddl-auto", () -> "validate");
	}

	@Autowired
	private AccountRepository accountRepository;

	@Override
	protected AccountRepository createRepository() {
		return accountRepository;
	}
}