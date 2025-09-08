package exceptions;

//Exception used to indicate the existence of a duplicate tourist outing in the system.
@SuppressWarnings("serial")
public class RepeatedTouristOutingException extends Exception {

	public RepeatedTouristOutingException (String string) {
		super(string);
	}
	
}
