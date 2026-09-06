package ca.vetClinic.application.service;

import ca.vetClinic.application.command.ChangePasswordCmd;
import ca.vetClinic.application.command.LoginCmd;
import ca.vetClinic.application.command.RegisterCmd;
import ca.vetClinic.application.command.UpdatePasswordCmd;
import ca.vetClinic.application.dto.AuthResult;
import ca.vetClinic.domain.enumerator.Role;
import ca.vetClinic.domain.exception.ConflictException;
import ca.vetClinic.domain.exception.ForbiddenException;
import ca.vetClinic.domain.model.Account;
import ca.vetClinic.domain.model.User;
import ca.vetClinic.domain.port.AuthenticatorPort;
import ca.vetClinic.domain.port.PasswordHasherPort;
import ca.vetClinic.domain.port.TokenPort;
import ca.vetClinic.domain.service.AccountService;
import ca.vetClinic.domain.service.AuthService;
import ca.vetClinic.domain.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
	private final AccountService accountService;
	private final UserService userService;
	private final PasswordHasherPort passwordHasher;
	private final AuthenticatorPort authenticatorPort;
	private final TokenPort tokenPort;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public AuthResult register(RegisterCmd cmd) {
		if (accountService.existsByEmail(cmd.email())) {
			throw new ConflictException("The account with this email already exists!");
		}
		Account account = new Account(null, cmd.email(), passwordHasher.encode(cmd.password()), Role.USER);
		accountService.save(account);
		User user = new User(null, account.getId(), cmd.firstName(), cmd.lastName(), cmd.phoneNumber(), null);
		userService.save(user);

		String token = tokenPort.generateToken(cmd.email(), cmd.role());

		return new AuthResult(token, "Bearer", tokenPort.getExpiration());
	}

	@Override
	public AuthResult login(LoginCmd cmd) {
		authenticatorPort.authenticate(cmd.email(), cmd.password());

		if (accountService.isMustChangePassword(cmd.email())) {
			throw new ForbiddenException("You must change the password!");
		}

		String token = tokenPort.generateToken(cmd.email(), cmd.role());

		return new AuthResult(token, "Bearer", tokenPort.getExpiration());
	}

	@Override
	public void changePassword(ChangePasswordCmd cmd) {
		Account account = accountService.findByEmail(cmd.email());
		UpdatePasswordCmd updateCmd = new UpdatePasswordCmd(cmd.oldPassword(), cmd.newPassword());
		accountService.updatePassword(account.getId(), updateCmd);
	}
}