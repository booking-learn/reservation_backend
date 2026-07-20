package ca.vetClinic.unit.service;

import ca.vetClinic.application.command.UpdateUserCmd;
import ca.vetClinic.application.service.UserServiceImpl;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

	private final String FIRST_NAME = "jacob";
	private final String LAST_NAME = "houle";
	private final String PHONE_NUMBER = "1234567890";
	private UpdateUserCmd cmd;
	@Mock
	private UserRepository userRepository;
	@Captor
	private ArgumentCaptor<User> captor;
	@Spy
	private User user = new User(null, null, FIRST_NAME, LAST_NAME, PHONE_NUMBER, null);
	private UserServiceImpl userService;
	private final UUID uuid = UUID.randomUUID();
	@BeforeEach
	void setUp() {
		userService = new UserServiceImpl(userRepository);
		cmd = new UpdateUserCmd(FIRST_NAME, LAST_NAME, PHONE_NUMBER);
	}
	@Test
	void givenWhenValidUpdateUserCmd_thenUserIsUpdated() {
		doReturn(uuid).when(user).getAccountId();
		when(userRepository.findByAccountId(uuid)).thenReturn(user);
		userService.updateUser(user.getAccountId(), cmd);
		verify(userRepository, times(1)).save(captor.capture());
	}

}