package exceptions;

//Exception used to indicate the absence of  tourist outings in the system. 
@SuppressWarnings("serial")
public class TouristOutingDoesNotExistException extends Exception {

	public TouristOutingDoesNotExistException(String string) {
		super(string);
	}

}
