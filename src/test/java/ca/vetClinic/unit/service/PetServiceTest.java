package ca.vetClinic.unit.service;

import ca.vetClinic.application.command.UpdatePetCmd;
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
	@Spy
	private Pet pet = new Pet(null, OWNER_ID, NAME, SPECIES, null, GENDER, BIRTHDATE);
	@Mock
	private User user;
	@Mock
	private Account account;
	@Mock
	private UpdatePetCmd updatePetCmd;

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
		void givenNullPet_thenThrowException() {
			assertThrows(IllegalArgumentException.class, () -> petService.save(null));
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
            when(account.getId()).thenReturn(UUID.randomUUID());
            when(userService.findByAccountId(any(UUID.class))).thenReturn(user);
            when(user.getId()).thenReturn(OWNER_ID);
            List<Pet> pets=List.of(pet);
            when(petRepository.findAllByUserId(OWNER_ID)).thenReturn(pets);
            List<Pet> result= petService.findAllByOwnerEmail(OWNER_EMAIL);
            assertEquals(result,pets);
        }
		@Test
		void givenFindByPetId_thenReturnPet() {
            when(petRepository.findById(PET_ID)).thenReturn(pet);
            Pet copy = petService.findByPetId(PET_ID);
            assertEquals(copy, pet);
		}

		@Test
		void givenFindOwnerId_thenReturnOwnerId() {
            when(accountService.findByEmail(OWNER_EMAIL)).thenReturn(account);
            doReturn(UUID.randomUUID()).when(account).getId();
            when(userService.findByAccountId(any(UUID.class))).thenReturn(user);
            doReturn(OWNER_ID).when(user).getId();
            assertEquals(OWNER_ID,petService.findOwnerId(OWNER_EMAIL));
		}

	}
	@Nested
	class Update {
		@Test
		void givenUpdate_thenUpdatePet() {
            when(petRepository.findById(PET_ID)).thenReturn(pet);
            petService.update(PET_ID, updatePetCmd);
            verify(petRepository).save(petCaptor.capture());
		}

		@Test
		void givenUpdateByOwner_thenUpdatePet() {
            when(accountService.findByEmail(OWNER_EMAIL)).thenReturn(account);
            when(account.getId()).thenReturn(UUID.randomUUID());
            when(userService.findByAccountId(any(UUID.class))).thenReturn(user);
            when(user.getId()).thenReturn(OWNER_ID);
            when(petRepository.findById(PET_ID)).thenReturn(pet);
            petService.updateByOwner(OWNER_EMAIL,PET_ID,updatePetCmd);
            verify(petRepository).save(petCaptor.capture());
		}
	}
	@Nested
	class Delete {
		@Test
		void givenDeleteById_thenDeletePet() {
			petService.deleteById(PET_ID);
			verify(petRepository).deleteById(PET_ID);
		}

		@Test
		void givenDeleteByOwnerEmail_thenDeletePet() {
            when(accountService.findByEmail(OWNER_EMAIL)).thenReturn(account);
            when(account.getId()).thenReturn(UUID.randomUUID());
            when(userService.findByAccountId(any(UUID.class))).thenReturn(user);
            when(user.getId()).thenReturn(OWNER_ID);
            petService.deleteByOwnerEmail(OWNER_EMAIL);
            verify(petRepository).deleteAllByUserId(OWNER_ID);
		}
	}

}