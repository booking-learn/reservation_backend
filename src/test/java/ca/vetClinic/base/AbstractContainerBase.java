package ca.vetClinic.base;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;

public abstract class AbstractContainerBase {
	static final MySQLContainer<?> mysql;

	static {
		mysql = new MySQLContainer<>("mysql:8").withDatabaseName("vet_clinic_db").withUsername("root")
				.withPassword("password");
		mysql.start();
	}

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
}
