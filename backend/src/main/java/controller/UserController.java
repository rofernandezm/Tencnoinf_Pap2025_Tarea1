package controller;

import java.util.ArrayList;
import java.util.List;

import dto.DtActivityWithOutings;
import dto.DtInscriptionTouristOuting;
import dto.DtSupplier;
import dto.DtSupplierProfile;
import dto.DtTourist;
import dto.DtTouristOuting;
import dto.DtTouristProfile;
import dto.DtUser;
import dto.DtUserProfile;
import dto.UserType;
import entity.Supplier;
import entity.Tourist;
import entity.TouristActivity;
import entity.User;
import exceptions.RepeatedUserEmailException;
import exceptions.RepeatedUserNicknameException;
import handler.TouristActivityHandler;
import handler.TouristOutingAndInscrptionHandler;
import handler.UserHandler;
import interfaces.IUserController;

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
