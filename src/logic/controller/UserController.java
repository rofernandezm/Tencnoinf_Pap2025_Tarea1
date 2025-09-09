package logic.controller;

import java.util.ArrayList;
import java.util.List;

import exceptions.RepeatedUserEmailException;
import exceptions.RepeatedUserNicknameException;
import logic.dto.DtActivityWithOutings;
import logic.dto.DtInscriptionTouristOuting;
import logic.dto.DtSupplier;
import logic.dto.DtSupplierProfile;
import logic.dto.DtTourist;
import logic.dto.DtTouristOuting;
import logic.dto.DtTouristProfile;
import logic.dto.DtUser;
import logic.dto.DtUserProfile;
import logic.dto.UserType;
import logic.entity.Supplier;
import logic.entity.Tourist;
import logic.entity.TouristActivity;
import logic.entity.User;
import logic.handler.TouristActivityHandler;
import logic.handler.TouristOutingAndInscrptionHandler;
import logic.handler.UserHandler;
import logic.interfaces.IUserController;

public class UserController implements IUserController {

	private DtUser dtUser;

	public void dataEntry(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException {

		this.dtUser = dtUser;

		UserHandler uh = UserHandler.getIntance();

		if (uh.existNickname(dtUser.getNickname()))
			throw new RepeatedUserNicknameException(
					"Error - El nombre de usuario ingresado ya está en uso. Por favor, elige otro.");
		if (uh.existEmail(dtUser.getEmail()))
			throw new RepeatedUserEmailException(
					"Error - El correo electrónico ingresado ya está en uso. Por favor, utiliza otro.");
	}

	public void cancelRegistration() {
		this.dtUser = null;
	}

	public void confirmRegistration() {
		UserHandler uh = UserHandler.getIntance();
		User user;
		if (dtUser.getUserType() == UserType.SUPPLIER) {
			DtSupplier supplier = (DtSupplier) dtUser;
			user = new Supplier(supplier);
		} else {
			DtTourist tourist = (DtTourist) dtUser;
			user = new Tourist(tourist);
		}

		uh.addUser(user);
	}

	public String[] listUsers() {
		return UserHandler.getIntance().listUsers();
	}

	public String[] listTourists() {
		return UserHandler.getIntance().listTourists();
	}

	public String[] listSuppliers() {
		return UserHandler.getIntance().listSuppliers();
	}

	// TODO
	public DtUserProfile selectUser(String nickname) {

		User selected = UserHandler.getIntance().getUserByNickname(nickname);
		TouristOutingAndInscrptionHandler toaih = TouristOutingAndInscrptionHandler.getIntance();

		if (selected instanceof Supplier) {
			TouristActivityHandler tah = TouristActivityHandler.getIntance();

			List<String> activities = tah.listTouristActivitiesBySupplierNickname(nickname);
			List<DtActivityWithOutings> dtActWithOut = new ArrayList<>();

			for (String act : activities) {

				TouristActivity ta = tah.getTouristActivityByName(act);
				List<DtTouristOuting> toList = toaih.getDtTouristOutingListByActivityName(nickname);
				dtActWithOut.add(new DtActivityWithOutings(ta.getDtTouristActivity(), toList));
			}

			return new DtSupplierProfile(selected.createDtUser(), dtActWithOut);

		} else {

			List<DtInscriptionTouristOuting> dts = toaih.getDtInscriptionTouristOutingListByTouristName(nickname);
			return new DtTouristProfile(selected.createDtUser(), dts);
		}
	}

	public DtUser consultUserData(String nickname) {
		User selected = UserHandler.getIntance().getUserByNickname(nickname);
		return selected.createDtUser();
	}
	
	public void modifyUserDate(DtUser dtUser) {
		UserHandler.getIntance().updateUser(dtUser);
	}
}
