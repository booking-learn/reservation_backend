package ca.vetClinic.unit.service;

import ca.vetClinic.application.service.PetServiceImpl;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.Pet;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.PetRepository;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.PetService;
import ca.vetClinic.domain.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PetServiceTest {

	private String NAME = "Henry";
	private String SPECIES = "Cat";
	private String BREED = null;
	private String GENDER = "MALE";
	private String OWNER_EMAIL = "gontran@outlook.com";
	private LocalDate BIRTHDATE = LocalDate.now().minusYears(5);
	private UUID OWNER_ID = UUID.randomUUID();
	private UUID PET_ID = UUID.randomUUID();

	@Mock
	private PetRepository petRepository;
	@Mock
	private UserService userService;
	@Mock
	private AccountService accountService;
	@Captor
	ArgumentCaptor<Pet> petCaptor;
	@Mock
	private Pet pet;
	@Mock
	private User user;
	@Mock
	private Account account;

	private PetService petService;

	@BeforeEach
	void setUp() {
		petService = new PetServiceImpl(petRepository, userService, accountService);
	}

	@Nested
	class SaveAndCreate {
		@Test
		void givenSavePet_thenSavePet() {
			petService.save(pet);
			verify(petRepository).save(petCaptor.capture());
		}
		@Test
		void givenCreatePet_thenCreatePet() {
			Pet otherPet = petService.createPet(OWNER_ID, NAME, SPECIES, BREED, GENDER, BIRTHDATE);
			assertEquals(Pet.class, otherPet.getClass());

		}

	}
	@Nested
	class Find {
		@Test
		void givenFindAllWithoutPet_tnenReturnEmptyList() {
			List<Pet> pets = petService.findAll();
			assertTrue(pets.isEmpty());
		}
		@Test
        void givenFindAllPet_tnenReturnListWithPet() {
            when(petRepository.findAll()).thenReturn(List.of(pet));
            List<Pet> pets=List.of(pet);
            List<Pet> result=petService.findAll();
            assertEquals(result,pets);
        }

		@Test
        void givenFindAllByOwnerEmail_thenReturnListWithPet() {
            when(accountService.findByEmail(OWNER_EMAIL)).thenReturn(account);
            doReturn(UUID.randomUUID()).when(account).getId();
            when(userService.findByAccountId(any(UUID.class))).thenReturn(user);
            doReturn(OWNER_ID).when(user).getId();
            List<Pet> pets=List.of(pet);
            when(petRepository.findAllByUserId(OWNER_ID)).thenReturn(pets);
            List<Pet> result= petService.findAllByOwnerEmail(OWNER_EMAIL);
            assertEquals(result,pets);
        }
		@Test
		void findByPetId() {
		}

		@Test
		void findOwnerId() {
		}

	}
	@Nested
	class Update {
		@Test
		void update() {
		}

		@Test
		void updateByOwner() {
		}
	}
	@Nested
	class Delete {
		@Test
		void deleteById() {
		}

		@Test
		void deleteByOwnerEmail() {
		}
	}

}