package ca.vetClinic.integration.repository;

import ca.vetClinic.base.AbstractContainerBase;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Pet;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.domain.repository.PetRepository;
import ca.vetClinic.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Sql(scripts = "/sql/CleanUp.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
class PetRepositoryTest extends AbstractContainerBase {

	private String NAME = "Henry";
	private String NEW_NAME = "gaspart";
	private LocalDate BIRTHDATE = LocalDate.now();
	private String SPECIES = "Cat";
	private String BREED = null;
	private String GENDER = "MALE";
	private final String EMAIL = "jacob@gmail.com";
	private final String PASSWORD = "qwerty";
	private final String FIRST_NAME = "Jacob";
	private final String LAST_NAME = "Tremblay";
	private final String PHONE_NUMBER = "418-555-1234";
	private UUID ownerId;
	private Pet pet;
	private Pet secondPet;
	private User user;
	private Account account;

	@Autowired
	private PetRepository repository;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private AccountRepository accountRepository;
	@BeforeEach
	void setUp() {
		account = new Account(null, EMAIL, PASSWORD, Role.USER);
		accountRepository.save(account);
		user = new User(null, account.getId(), FIRST_NAME, LAST_NAME, PHONE_NUMBER, Instant.now());
		userRepository.save(user);
		ownerId = user.getId();
		pet = new Pet(null, ownerId, NAME, SPECIES, BREED, GENDER, BIRTHDATE);
		secondPet = new Pet(null, ownerId, NEW_NAME, SPECIES, BREED, GENDER, BIRTHDATE);
	}

	@Nested
	class Save {
		@Test
		void givenSave_thenSavePet() {
			repository.save(pet);
			Pet otherPet = repository.findById(pet.getId());
			assertEquals(pet, otherPet);
		}
		@Test
		void givenUpdate_thenUpdatePet() {
			repository.save(pet);
			pet.setName(NEW_NAME);
			repository.save(pet);
			Pet copy = repository.findById(pet.getId());
			assertEquals(copy.getName(), pet.getName());
		}
	}

	@Nested
	class Find {
		@Test
		void givenFindAll_thenReturnAccurateNumbersOfPet() {
			repository.save(pet);
			repository.save(secondPet);
			List<Pet> pets = repository.findAll();
			assertEquals(2, pets.size());
		}

		@Test
		void givenFindAllByUserId_thenReturnAccurateNumbersOfPet() {
			repository.save(pet);
			repository.save(secondPet);
			List<Pet> pets = repository.findAllByUserId(ownerId);
			assertEquals(2, pets.size());
		}
	}

	@Nested
	class Delete {
		@Test
		void givenDeleteById_thenDeletePet() {
			repository.save(pet);
			repository.deleteById(pet.getId());
			assertTrue(repository.findAll().isEmpty());
		}
		@Test
		void givenDeleteAllByUserId_thenDeletePet() {
			repository.save(pet);
			repository.deleteById(pet.getId());
			assertTrue(repository.findAllByUserId(ownerId).isEmpty());
		}
	}

}