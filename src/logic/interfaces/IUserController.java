package logic.interfaces;

import logic.dto.DtUser;
import logic.dto.DtUserProfile;

public interface IUserController {

	public Boolean dataEntry(DtUser dtUser);

	public void cancelRegistration();

	public void confirmRegistration();

	public String[] listUsers();

	public String[] listTourists();

	public String[] listSuppliers();

	public DtUserProfile selectUser(String nickname);

	public DtUser consultUserData(String nickname);

}
