package ca.vetClinic.domain.repository;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.Account;

import java.util.List;

public interface CareOfferingRepository {
	List<Account> findAll();

	Account findByService(CareService service);
}
