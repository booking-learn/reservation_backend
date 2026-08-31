package ca.vetClinic.application.service;

import ca.vetClinic.application.command.UpdateUserCmd;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.PetRepository;
import ca.vetClinic.domain.repository.UserRepository;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.PetService;
import ca.vetClinic.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final AccountService accountService;
	private final PetRepository petRepository;
	private void validateUUID(UUID uuid) {
		if (uuid == null) {
			throw new IllegalArgumentException("UUID is null");
		}
	}
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(UUID id) {
		validateUUID(id);
		return userRepository.findById(id);
	}

	@Override
	public void save(User user) {
		if (user.getAccountId() == null) {
			throw new IllegalArgumentException("User must have an account id");
		}
		userRepository.save(user);
	}

	@Override
	public void delete(UUID id) {
		validateUUID(id);
		User user = userRepository.findById(id);
		accountService.deleteById(user.getAccountId());
		petRepository.deleteAllByUserId(user.getId());
		userRepository.delete(id);

	}

	@Override
	public User findByAccountId(UUID id) {
		validateUUID(id);
		return userRepository.findByAccountId(id);
	}

	@Override
	public void updateUser(UUID accountId, UpdateUserCmd cmd) {
		validateUUID(accountId);
		User user = userRepository.findByAccountId(accountId);
		user.setFirstName(cmd.firstName());
		user.setLastName(cmd.lastName());
		user.setPhoneNumber(String.valueOf(cmd.phoneNumber()));
		userRepository.save(user);
	}

}
