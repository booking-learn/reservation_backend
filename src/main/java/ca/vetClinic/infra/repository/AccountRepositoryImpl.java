package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.exception.NotFoundException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.AccountRepository;
import ca.vetClinic.infra.entity.AccountEntity;
import ca.vetClinic.infra.mapper.AccountMapper;
import ca.vetClinic.infra.repository.jpa.AccountJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class AccountRepositoryImpl implements AccountRepository {

	private final AccountJpaRepository jpaRepository;
	private final AccountMapper mapper;

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public List<Account> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toAccount).toList();
	}

	@Override
	public Account findById(UUID id) {
		return jpaRepository.findById(id).map(mapper::toAccount).orElseThrow(() -> new NotFoundException("id"));
	}

	@Override
	public void deleteById(UUID id) {
		jpaRepository.deleteById(id);
	}

	@Override
	public void save(Account account) {
		AccountEntity entity = mapper.toEntity(account);
		// entityManager.persist(entity);
		jpaRepository.save(entity);
	}

	@Override
	public Account findByEmail(String email) {
		return jpaRepository.findByEmail(email).map(mapper::toAccount)
				.orElseThrow(() -> new NotFoundException("email"));
	}

	@Override
	public boolean existsByEmail(String email) {
		return jpaRepository.findByEmail(email).isPresent();
	}
}