package ca.vetClinic.infra.mapper;

import ca.vetClinic.domain.model.Account;
import ca.vetClinic.infra.entity.AccountEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
	AccountEntity toEntity(Account account);
	Account toAccount(AccountEntity accountEntity);
}
