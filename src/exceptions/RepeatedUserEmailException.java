package exceptions;

public class RepeatedUserEmailException extends Exception {
	public RepeatedUserEmailException(String string) {
		super(string);
	}
}
