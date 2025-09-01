package exceptions;

public class RepeatedUserNicknameException extends Exception {
	public RepeatedUserNicknameException(String string) {
		super(string);
	}
}