package ca.vetClinic.integration.repository;

import ca.vetClinic.base.AbstractContainerBase;
import ca.vetClinic.domain.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Sql(scripts = "/sql/CleanUp.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PetRepositoryTest extends AbstractContainerBase {

	@Autowired
	private PetRepository petRepository;
	@BeforeEach
	void setUp() {
	}

	@Nested
	class Save {
		@Test
		void save() {
		}
	}
	@Nested
	class Find {
		@Test
		void findById() {
		}

		@Test
		void findAll() {
		}

		@Test
		void findByUserId() {
		}

		@Test
		void findAllByUserId() {
		}

	}
	@Nested
	class Delete {
		@Test
		void deleteById() {
		}

		@Test
		void deleteAllByUserId() {
		}
	}

}