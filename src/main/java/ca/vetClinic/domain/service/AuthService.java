package ca.vetClinic.domain.service;

import ca.vetClinic.application.command.ChangePasswordCmd;
import ca.vetClinic.application.command.LoginCmd;
import ca.vetClinic.application.command.RegisterCmd;
import ca.vetClinic.application.dto.AuthResult;

public interface AuthService {

	AuthResult register(RegisterCmd cmd);

	AuthResult login(LoginCmd cmd);

	void changePassword(ChangePasswordCmd cmd);

}
