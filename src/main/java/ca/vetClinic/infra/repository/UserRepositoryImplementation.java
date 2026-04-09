package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.UserRepository;
import ca.vetClinic.infra.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImplementation implements UserRepository {

	private UserJpaRepository userJpaRepository;
	private UserMapper mapper;

	@Override
	public List<User> findAll() {
		return userJpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}

	@Override
	public User findById(UUID id) {
		return userJpaRepository.findById(id).map(mapper::toDomain).orElseThrow(() -> new NotFoundException("id"));
	}

	@Override
	public void save(User user) {
		userJpaRepository.save(mapper.toEntity(user));
	}

	@Override
	public void delete(UUID id) {
		userJpaRepository.deleteById(id);
	}
}
