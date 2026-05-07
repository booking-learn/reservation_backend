package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.Account;
import ca.vetClinic.infra.security.UserPrincipal;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPrincipalMapper {
	UserPrincipal fromAccount(Account account);
}
