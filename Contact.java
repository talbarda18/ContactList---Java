/**
* The Contact Class implements a contact object.
* each contact has: first name and family name.
* Contact is a Comparable type, Override compareTo and
* compare by name.
*
* @author  Tal Barda
* @Date   20-12-2021 
*/

import java.util.InputMismatchException;

public class Contact implements Comparable<Contact>{

	private String firstName;
	private String familyName;
	
	public Contact(String firstName, String familyName){
		setFirstName(firstName); 
		setFamilyName(familyName);
	}
	
	public String getFirstName() {
		return this.firstName;
	}
	
	public String getFamilyName() {
		return this.familyName;
	}
	
	public void setFirstName(String name) throws InputMismatchException{
			if(validateName(name)) {
				this.firstName = name;
			}
			else 
				throw new InputMismatchException(name + "Can't is not valid name.\n"
						+ " Name must contain only aplhabet letters");
	}
	
	public void setFamilyName(String name) {
		if(validateName(name)) {
			this.familyName = name;
		}
		else 
			throw new InputMismatchException(name + "Can't is not valid name.\n"
					+ " Name must contain only aplhabet letters");
	}	
	
	public String toString() {
		return String.format("%-15s%-15s",this.firstName,this.familyName); 
	}
	
	@Override
	public int compareTo(Contact contactB) {
		if(this.familyName.compareTo(contactB.getFamilyName()) > 0) {
			return 1;
		}
		else if(this.familyName.compareTo(contactB.getFamilyName()) < 0) {
			return -1;
		}
		// case the family names are the same ->> sort by first name
		else if(this.firstName.compareTo(contactB.getFirstName()) > 0){
			return 1;
		}
		else if(this.firstName.compareTo(contactB.getFirstName()) < 0) {
			return -1;
		}
		else {
			return 0;
		}
	}
	
	// validateName method
	// check validation for names - contains
	public boolean validateName(String name) {
		return name.matches("[a-zA-Z]+|[a-zA-Z]+\\s[a-zA-Z]+");
	}
	// end of validateName
	
}
