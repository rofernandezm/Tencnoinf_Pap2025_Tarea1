package logic.controller;

import java.time.LocalDate;

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

	public Boolean dataEntry(DtUser dtUser) {

		this.dtNewUser = dtUser;

		UserHandler uh = UserHandler.getIntance();
		return (uh.existEmail(dtUser.getEmail()) || uh.existNickname(dtUser.getNickname()));
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
		return UserHandler.getIntance().listUsers();
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
