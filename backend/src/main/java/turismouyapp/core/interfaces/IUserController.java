package turismouyapp.core.interfaces;

import turismouyapp.core.dto.DtUser;
import turismouyapp.core.dto.DtUserProfile;
import turismouyapp.core.exceptions.RepeatedUserEmailException;
import turismouyapp.core.exceptions.RepeatedUserNicknameException;

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
