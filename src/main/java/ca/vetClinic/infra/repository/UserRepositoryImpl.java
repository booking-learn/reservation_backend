package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.repository.UserRepository;
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

	private UserJpaRepository jpaRepository;
	private UserMapper mapper;
	private AccountJpaRepository JpaRepository;

	@Override
	public List<User> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}

	@Override
	public User findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toDomain).orElseThrow(() -> new NotFoundException("id"));
	}

	@Override
	public void save(User user) {
		jpaRepository.save(mapper.toEntity(user));
	}

	@Override
	public void delete(UUID id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public User findByAccountId(UUID id) {
		return jpaRepository.findByAccountId(id).map(mapper::toDomain)
				.orElseThrow(() -> new NotFoundException("accountId"));
	}
}
