package ca.vetClinic.domain.service;

import ca.vetClinic.application.command.UpdateUserCmd;
import ca.vetClinic.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
	List<User> findAll();

	User findById(UUID id);

	void save(User user);

	void deleteById(UUID id);

	User findByAccountId(UUID accountId);

	void updateUser(UUID accountId, UpdateUserCmd cmd);

}
