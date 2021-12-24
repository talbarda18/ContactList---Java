/**
* The Main Class define InvalidArgumentException as subClass of Exception
*
* @author  Tal Barda
* @Date   20-12-2021 
*/

public class InvalidArgumentException extends Exception{
	public InvalidArgumentException() {
		super();
	}
	public InvalidArgumentException(String s) {
		super(s);
	}
}
