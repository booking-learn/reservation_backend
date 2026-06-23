package ca.vetClinic.api.mapper;

import ca.vetClinic.api.dto.UserDto;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
	public UserDto toDto(User user, Account account) {
		return new UserDto(user.getId(), account.getEmail(), user.getFirstName(), user.getLastName(),
				account.getRole());
	}
}
