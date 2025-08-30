package exceptions;

//Exception used to indicate the absence of  tourist activities in the system. 

public class ActivityDoesNotExistException extends Exception {

	public ActivityDoesNotExistException(String string) {
        super(string); 
    }
}
