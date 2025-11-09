package turismouyapp.core.controller;

import java.util.ArrayList;
import java.util.List;

import turismouyapp.core.dto.DtActivityWithOutings;
import turismouyapp.core.dto.DtInscriptionTouristOuting;
import turismouyapp.core.dto.DtSupplier;
import turismouyapp.core.dto.DtSupplierProfile;
import turismouyapp.core.dto.DtTourist;
import turismouyapp.core.dto.DtTouristOuting;
import turismouyapp.core.dto.DtTouristProfile;
import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.DtUserProfile;
import turismouyapp.core.dto.UserType;
import turismouyapp.core.entity.Supplier;
import turismouyapp.core.entity.Tourist;
import turismouyapp.core.entity.TouristActivity;
import turismouyapp.core.entity.User;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;
import turismouyapp.core.handler.TouristActivityHandler;
import turismouyapp.core.handler.TouristOutingAndInscrptionHandler;
import turismouyapp.core.handler.UserHandler;
import turismouyapp.core.interfaces.IUserController;

public class UserController implements IUserController {

	private DtUser dtUser;

	public void dataEntry(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException {

		// this.dtUser = dtUser;

		UserHandler uh = UserHandler.getIntance();

		if (uh.existNickname(dtUser.getNickname()))
			throw new RepeatedUserNicknameException(
					"Error - El nombre de usuario ingresado ya está en uso. Por favor, elige otro.");
		if (uh.existEmail(dtUser.getEmail()))
			throw new RepeatedUserEmailException(
					"Error - El correo electrónico ingresado ya está en uso. Por favor, utiliza otro.");

		User user;
		if (dtUser instanceof DtSupplier) {
			user = new Supplier((DtSupplier) dtUser);
			
		} else if (dtUser instanceof DtTourist) {
			user = new Tourist((DtTourist) dtUser);
			
		} else {
			
			throw new IllegalArgumentException("Tipo de usuario no válido");
		}

		uh.addUser(user);

	}

	public void dataEntrySupplier(DtSupplier dtSupplier)
			throws RepeatedUserEmailException, RepeatedUserNicknameException {

		UserHandler uh = UserHandler.getIntance();

		if (uh.existNickname(dtSupplier.getNickname()))
			throw new RepeatedUserNicknameException(
					"Error - El nombre de usuario ingresado ya está en uso. Por favor, elige otro.");
		if (uh.existEmail(dtSupplier.getEmail()))
			throw new RepeatedUserEmailException(
					"Error - El correo electrónico ingresado ya está en uso. Por favor, utiliza otro.");

		uh.addUser(new Supplier(dtSupplier));

	}

	public void dataEntryTourist(DtTourist dtTourist) throws RepeatedUserEmailException, RepeatedUserNicknameException {

		UserHandler uh = UserHandler.getIntance();

		if (uh.existNickname(dtTourist.getNickname()))
			throw new RepeatedUserNicknameException(
					"Error - El nombre de usuario ingresado ya está en uso. Por favor, elige otro.");
		if (uh.existEmail(dtTourist.getEmail()))
			throw new RepeatedUserEmailException(
					"Error - El correo electrónico ingresado ya está en uso. Por favor, utiliza otro.");

		uh.addUser(new Tourist(dtTourist));
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
		if (selected == null) {
			return null;
		}
		return selected.createDtUser();
	}

	public void modifyUserData(DtUser dtUser) {
		UserHandler.getIntance().updateUser(dtUser);
	}

	public DtUser consultUserDataByEmail(String email) {
		User selected = UserHandler.getIntance().getUserByEmail(email);
		if (selected == null) {
			return null;
		}
		return selected.createDtUser();
	}
}
