package logic.interfaces;

import exceptions.RepeatedUserEmailException;
import exceptions.RepeatedUserNicknameException;
import logic.dto.DtUser;
import logic.dto.DtUserProfile;

public interface IUserController {

	public void dataEntry(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException;

	public void cancelRegistration();

	public void confirmRegistration();

	public String[] listUsers();

	public String[] listTourists();

	public String[] listSuppliers();

	public DtUserProfile selectUser(String nickname);

	public DtUser consultUserData(String nickname);

}
