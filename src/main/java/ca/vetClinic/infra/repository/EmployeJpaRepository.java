package ca.vetClinic.infra.repository;

import ca.vetClinic.infra.entity.EmployeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeJpaRepository extends JpaRepository<EmployeEntity, UUID> {
}
