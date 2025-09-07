package logic.controller;

import java.time.LocalDate;

import exceptions.RepeatedUserEmailException;
import exceptions.RepeatedUserNicknameException;
import logic.dto.DtSupplier;
import logic.dto.DtTourist;
import logic.dto.DtUser;
import logic.dto.DtUserProfile;
import logic.dto.UserType;
import logic.entity.Supplier;
import logic.entity.Tourist;
import logic.entity.User;
import logic.handler.UserHandler;
import logic.interfaces.IUserController;

public class UserController implements IUserController {

	private DtUser dtNewUser;

	public void dataEntry(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException {

		this.dtNewUser = dtUser;

		UserHandler uh = UserHandler.getIntance();

		if (uh.existNickname(dtUser.getNickname()))
			throw new RepeatedUserNicknameException("Error - El nombre de usuario ingresado ya está en uso. Por favor, elige otro.");
		if (uh.existEmail(dtUser.getEmail()))
			throw new RepeatedUserEmailException("Error - El correo electrónico ingresado ya está en uso. Por favor, utiliza otro.");
	}

	public void cancelRegistration() {
		this.dtNewUser = null;
	}

	public void confirmRegistration() {
		UserHandler uh = UserHandler.getIntance();
		User newUser = dtNewUser.toEntity();
		uh.addUser(newUser);
	}

	public String[] listUsers() {
		return UserHandler.getIntance().listUsers();
	}

	public String[] listTourists() {
		return UserHandler.getIntance().listUsers();
	}

	public String[] listSuppliers() {
		return UserHandler.getIntance().listSuppliers();
	}

	public DtUserProfile selectUser(String nickname) {

		User selected = UserHandler.getIntance().getUserByNickname(nickname);
		return selected.createDtUserProfile();
	}

	public DtUser consultUserData(String nickname) {
		User selected = UserHandler.getIntance().getUserByNickname(nickname);
		return selected.createDtUser();
	}
}
