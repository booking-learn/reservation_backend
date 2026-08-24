package ca.vetClinic.unit.service;

import ca.vetClinic.application.command.UpdateUserCmd;
import ca.vetClinic.application.service.UserServiceImpl;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.UserRepository;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.PetService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private final String FIRST_NAME = "jacob";
	private final String LAST_NAME = "houle";
	private final String PHONE_NUMBER = "1234567890";
	private final String NEW_FIRST_NAME = "gontran";
	private final String NEW_LAST_NAME = "matondo";
	private final String NEW_PHONE_NUMBER = "1294567890";
	private UpdateUserCmd cmd;
	@Mock
	private UserRepository userRepository;
	@Mock
	private AccountService accountService;
	@Mock
	private PetService petService;
	@Captor
	private ArgumentCaptor<User> captor;
	@Spy
	private User user = new User(null, null, FIRST_NAME, LAST_NAME, PHONE_NUMBER, null);
	private UserServiceImpl userService;
	private final UUID uuid = UUID.randomUUID();
	@BeforeEach
	void setUp() {
		userService = new UserServiceImpl(userRepository, accountService, petService);
		cmd = new UpdateUserCmd(NEW_FIRST_NAME, NEW_LAST_NAME, NEW_PHONE_NUMBER);
	}
	@Nested
	class Update {
		@Test
		void givenWhenValidUpdateUserCmd_thenUserIsUpdated() {
			doReturn(uuid).when(user).getAccountId();
			when(userRepository.findByAccountId(uuid)).thenReturn(user);
			userService.updateUser(user.getAccountId(), cmd);
			verify(userRepository, times(1)).save(captor.capture());
		}
	}

	@Nested
	class Delete {
		@Test
		void givenValidId_thenDeleteIsCalledOnRepository() {
            when(userRepository.findById(uuid)).thenReturn(user);
			userService.delete(uuid);
			verify(userRepository, times(1)).delete(uuid);
		}
		@Test
		void givenNullId_thenThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> userService.delete(null));
			verify(userRepository, never()).delete(any());
		}
		@Test
		void givenValidId_thenDeleteIsCalledOnAccountService() {
			UUID id = UUID.randomUUID();
			doReturn(id).when(user).getAccountId();
			when(userRepository.findById(uuid)).thenReturn(user);
			userService.delete(uuid);
			verify(accountService, times(1)).deleteById(id);
		}
	}
	@Nested
	class Find {
		@Test
		void thenReturnAllUsers() {
			List<User> users = List.of(new User(uuid, UUID.randomUUID(), FIRST_NAME, LAST_NAME, PHONE_NUMBER, null));
			when(userRepository.findAll()).thenReturn(users);

			List<User> result = userService.findAll();

			assertEquals(users, result);
		}
		@Test
		void givenValidId_thenReturnUser() {
			User user = new User(uuid, UUID.randomUUID(), FIRST_NAME, LAST_NAME, PHONE_NUMBER, null);
			when(userRepository.findById(uuid)).thenReturn(user);

			User result = userService.findById(uuid);

			assertEquals(user, result);
		}
		@Test
		void givenNullId_thenThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> userService.findById(null));
			verify(userRepository, never()).findById(any());
		}
		@Test
		void givenValidAccountId_thenReturnUser() {
			User user = new User(uuid, UUID.randomUUID(), FIRST_NAME, LAST_NAME, PHONE_NUMBER, null);
			when(userRepository.findByAccountId(uuid)).thenReturn(user);

			User result = userService.findByAccountId(uuid);

			assertEquals(user, result);
		}
		@Test
		void givenNullAccountId_thenThrowIllegalArgumentException() {
			assertThrows(IllegalArgumentException.class, () -> userService.findByAccountId(null));
			verify(userRepository, never()).findByAccountId(any());
		}
	}

}