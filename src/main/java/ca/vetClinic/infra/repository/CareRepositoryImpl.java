package ca.vetClinic.infra.repository;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.repository.CareOfferingRepository;

import java.util.List;

public class CareRepositoryImpl implements CareOfferingRepository {
	@Override
	public List<Account> findAll() {
		return List.of();
	}

	@Override
	public Account findByService(CareService service) {
		return null;
	}
}
