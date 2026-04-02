package ca.vetClinic.infra.persistence;

import ca.vetClinic.infra.entity.EmployeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeRepository extends JpaRepository<EmployeEntity, UUID> {
}
