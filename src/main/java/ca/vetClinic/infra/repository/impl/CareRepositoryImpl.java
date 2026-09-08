package ca.vetClinic.infra.repository.impl;

import ca.vetClinic.domain.enumerator.CareService;
import ca.vetClinic.domain.model.CareOffering;
import ca.vetClinic.domain.repository.CareOfferingRepository;
import ca.vetClinic.infra.mapper.CareOfferingMapper;
import ca.vetClinic.infra.repository.jpa.CareOfferingJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class CareRepositoryImpl implements CareOfferingRepository {
	private final CareOfferingJpaRepository jpaRepository;
	private final CareOfferingMapper mapper;

	@Override
	public List<CareOffering> findAll() {
		return jpaRepository.findAll().stream().map(mapper::toDomain).toList();
	}

	@Override
	public List<CareOffering> findByService(CareService service) {
		return jpaRepository.findByCareService(service).stream().map(mapper::toDomain).toList();
	}
}
