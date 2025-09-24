package exceptions;

@SuppressWarnings("serial")
public class RepeatedUserNicknameException extends Exception {
	public RepeatedUserNicknameException(String string) {
		super(string);
	}
}