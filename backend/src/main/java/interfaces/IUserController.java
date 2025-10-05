package interfaces;

import dto.DtUser;
import dto.DtUserProfile;
import exceptions.RepeatedUserEmailException;
import exceptions.RepeatedUserNicknameException;

public interface IUserController {

	public void dataEntry(DtUser dtUser) throws RepeatedUserEmailException, RepeatedUserNicknameException;

	public void cancelRegistration();

	public void confirmRegistration();

	public String[] listUsers();

	public String[] listTourists();

	public String[] listSuppliers();

	public DtUserProfile selectUser(String nickname);

	public DtUser consultUserData(String nickname);

	public void modifyUserDate(DtUser dtUser);
}
