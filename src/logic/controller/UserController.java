package logic.controller;

import logic.dto.DtUser;
import logic.dto.DtUserProfile;
import logic.entity.User;
import logic.handler.UserHandler;
import logic.interfaces.IUserController;

public class UserController implements IUserController {

	public Boolean dataEntry(DtUser dtUser) {
		return true;
	}

	public void cancelRegistration() {
	}

	public void confirmRegistration() {
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

		User selected = UserHandler.getIntance().gerUserByNickname(nickname);
		return selected.createDtUserProfile();
	}

	public DtUser consultUserData(String nickname) {
		User selected = UserHandler.getIntance().gerUserByNickname(nickname);
		return selected.createDtUser();
	}
}
