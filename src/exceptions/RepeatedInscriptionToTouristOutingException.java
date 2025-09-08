package exceptions;

//Exception used to indicate the existence of a duplicate inscription in the system.
@SuppressWarnings("serial")
public class RepeatedInscriptionToTouristOutingException extends Exception {

	public RepeatedInscriptionToTouristOutingException (String string) {
		super(string);
	}
	
}
