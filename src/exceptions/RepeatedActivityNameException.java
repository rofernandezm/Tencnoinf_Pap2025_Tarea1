package exceptions;

@SuppressWarnings("serial")
public class RepeatedActivityNameException extends Exception {
	public RepeatedActivityNameException(String string) {
		super(string);
	}
}