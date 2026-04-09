package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.model.User;

import java.util.List;
import java.util.UUID;

public interface UserRepository {
	List<User> findAll();

	User findById(UUID id);

	void save(User user);

	void delete(UUID id);
}
