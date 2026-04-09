package ca.vetClinic.application.service;

import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.UserRepository;
import ca.vetClinic.domain.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImplementation implements UserService {

	private UserRepository userRepository;

	@Autowired
	public UserServiceImplementation(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(UUID id) {
		return userRepository.findById(id);
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

	@Override
	public void deleteById(UUID id) {
		userRepository.delete(id);
	}
}
