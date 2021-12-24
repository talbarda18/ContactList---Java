/**
* The Main Class define MissingArgumentException as subClass of Exception
*
* @author  Tal Barda
* @Date   20-12-2021 
*/

public class MissingArgumentException extends Exception{
	public MissingArgumentException() {
		super();
	}
	public MissingArgumentException(String s) {
		super(s);
	}
}
