package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.UserRepository;
import ca.vetClinic.infra.entity.AccountEntity;
import ca.vetClinic.infra.entity.UserEntity;
import ca.vetClinic.infra.mapper.UserMapper;
import ca.vetClinic.infra.repository.jpa.AccountJpaRepository;
import ca.vetClinic.infra.repository.jpa.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

	private final UserJpaRepository jpaRepository;
	private final UserMapper mapper;

	@Override
	public List<User> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}

	@Override
	public User findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toDomain)
				.orElseThrow(() -> new NotFoundException("User not found"));
	}

	@Override
	public void save(User user) {
		if (user.getId() != null) {
			UserEntity existingUser = jpaRepository.findById(user.getId())
					.orElseThrow(() -> new NotFoundException("User not found with id: " + user.getId()));
			existingUser.setFirstName(user.getFirstName());
			existingUser.setLastName(user.getLastName());
			existingUser.setPhoneNumber(user.getPhoneNumber());
			jpaRepository.save(existingUser);
			return;
		}
		UserEntity entity = mapper.toEntity(user);
		jpaRepository.save(entity);
		user.setId(entity.getId());
	}

	@Override
	public void delete(UUID id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public User findByAccountId(UUID id) {
		return jpaRepository.findByAccountId(id).map(mapper::toDomain)
				.orElseThrow(() -> new NotFoundException("Account not found"));
	}
}