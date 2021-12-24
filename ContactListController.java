/**
* The ContactController Class manage all the contact list user interface
* creating new AssociativeTable, adding new contacts to list, 
* updating contact phone number, removing contact from list,
* searching contact in list and display the list. 
*
* @author  Tal Barda
* @Date   20-12-2021 
*/
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.StageStyle;

public class ContactListController implements Serializable{

    @FXML
    private TextField firstNameTextField;
    @FXML
    private TextField familyNameTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private Button searchButton;
    @FXML
    private Button addButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button updateButton;
    @FXML
    private TextArea firstNameTextArea;
    @FXML
    private TextArea familyNameTextArea;
    @FXML
    private TextArea phoneNumberTextArea;
    @FXML
    private Button showListButton;
    @FXML
    private Button saveButton;
    @FXML
    private Button loadButton;
    
    
    private String firstName;
    private String familyName;
    private String phoneNumber;
    private AssociativeTable<Contact, String> contactList; 
    private Contact contact;
    private File contactFile = new File("ContactsFile.txt");
    private int loadFlag = 0;
    
    // initialize method
    public void initialize() {
    	contactList = new AssociativeTable<Contact, String>();
    }
    //end of initialize
    
    // addButtonPressed method
    @FXML
    void addButtonPressed(ActionEvent event) {
    	firstName = firstNameTextField.getText();
    	familyName = familyNameTextField.getText();
    	phoneNumber = phoneNumberTextField.getText();
    	
    	// crating new contact object and add it to list
    	try {
    		contact = new Contact(firstName, familyName);
    		addNewContactToList(contact, phoneNumber);
    		displayList();
    	}
    	// display error window if catch input error
    	catch(InputMismatchException e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.initStyle(StageStyle.UTILITY);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error");
    		alert.setContentText("Names must contain only alphabet characters");
    		alert.showAndWait();
    	}
    }
    // end of addButtonPressed
	
    // addNewContactToList method
    private void addNewContactToList(Contact contact, String phoneNumber) {
		contactList.add(contact, phoneNumber);		
	}
    // end of addNewContactToList

    // deleteButtonPressed method
	@FXML
    void deleteButtonPressed(ActionEvent event) {
		try {
			// get the name to remove
	    	firstName = firstNameTextField.getText();
	    	familyName = familyNameTextField.getText();
	    	// must have both first and family name to remove
	    	if(firstName.isEmpty() || familyName.isEmpty()) {
	    		throw new MissingArgumentException("Delete must insert first name and family name");
	    	}
	    	// iterate through contactList to find the contact to remove
	    	Iterator<Contact> iterator = contactList.KeyIterator();
			while(iterator.hasNext()) {
				Contact key = (Contact)iterator.next();
				if(checkContains(key, firstName, familyName)) {
					contactList.remove(key);
					// display the updated list 
					displayList();
				}
			}
    	}
		// display error window if catch argument error
    	catch(MissingArgumentException e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.initStyle(StageStyle.UTILITY);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error");
    		alert.setContentText("Delete must insert first name and family name");
    		alert.showAndWait();
    	}	
    }

	// mouseAction methods 
	// set graphics to buttons on mouse events
    @FXML
    void mouseEnteredAction(MouseEvent event) {
    	((Button)event.getTarget()).setStyle("-fx-background-color: #483D8B;-fx-background-radius: 50em; -fx-text-fill: white");
    }

    @FXML
    void mouseExitedAction(MouseEvent event) {
    	((Button)event.getTarget()).setStyle("-fx-background-color: #191970;-fx-background-radius: 50em; -fx-text-fill: white");
    }
    // end of mouseAction methods

    // loadButtonPressed method
    // loads and display contactList from file
    @FXML
    void loadButtonPressed(ActionEvent event) throws IOException{
    	try {
    		if(loadFlag == 0) {// loadFlag prevent from user to load the file twice until new save has done
    		readData(contactFile);
    		// display the loaded list 
    		displayList();
    		loadFlag = 1;
    		}
    	}
    	// display error window if catch IOException error
    	catch(IOException e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.initStyle(StageStyle.UTILITY);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error");
    		alert.setContentText("Could not Load to file");
    		alert.showAndWait();
    	}
    }
    // end of loadButtonPressed
    
    // readData method
    // reading from file with input stream
    private void readData(File contactFile) throws IOException{
    	FileInputStream fIn = new FileInputStream(contactFile);
		DataInputStream in = new DataInputStream(fIn);
		String firstNameR;
		String familyNameR;
		// using ArrayLists to store the contacts and phone numbers loaded from file  
		ArrayList <Contact> readContactArr = new ArrayList<Contact>();
		ArrayList <String> readPhoneNum = new ArrayList<String>();
		int i = 0;
		String dataToDisplay = new String();
		try {
			// run to end of file till EOFException
			while(true) {
				firstNameR = in.readUTF();
				familyNameR = in.readUTF();
				readContactArr.add(new Contact(firstNameR, familyNameR));  
				readPhoneNum.add(in.readUTF());
			}
		}
		// when finished reading, create the contactList from loaded data
		catch(EOFException e) {
			contactList = new AssociativeTable<Contact, String>(readContactArr, readPhoneNum);
		}
	}
    // end readData

    // saveButtonPressed method
    // save the contactList to file
	@FXML
    void saveButtonPressed(ActionEvent event) throws IOException{
    	try {
	    	contactFile.createNewFile();
	    	writeData(contactFile);
	    	// reset loadFlag
	    	loadFlag = 0;
    	}
    	// display error window if catch IOException error
    	catch(IOException e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.initStyle(StageStyle.UTILITY);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error");
    		alert.setContentText("Could not Save to file");
    		alert.showAndWait();
    	}
    }
    
	// writeData method
	// writing to file with output stream
    private void writeData(File contactFile) throws IOException {
    	FileOutputStream fOut = new FileOutputStream(contactFile);
		DataOutputStream out = new DataOutputStream(fOut);
		// iterate through contactList and write the data to the file
		Iterator<Contact> iterator = contactList.KeyIterator();
		while(iterator.hasNext()) {
			Contact key = (Contact)iterator.next();
			out.writeUTF(key.getFirstName());
			out.writeUTF(key.getFamilyName());
			out.writeUTF(contactList.get(key));
		}
		out.close();
	}
    // end writingData

    // showListButtonPressed method
	@FXML
    void showListButtonPressed(ActionEvent event) {
    	displayList();
    }
	// end of showListButtonPressed

	// searchButtonPressed method
    @FXML
    void searchButtonPressed(ActionEvent event) throws Exception{
    	try {
	    	firstName = firstNameTextField.getText();
	    	familyName = familyNameTextField.getText();
	    	// must have both first and family name to search
	    	if(firstName.isEmpty() || familyName.isEmpty()) {
	    		throw new MissingArgumentException("Search must insert first name and family name");
	    	}
	    	// iterate through contactList to find the searched contact
	    	Iterator<Contact> iterator = contactList.KeyIterator();
			while(iterator.hasNext()) {
				Contact key = (Contact)iterator.next();
				if(checkContains(key, firstName, familyName)) {
					// display the contact 
					displayContact(key);
				}
			}
    	}
    	// display error window if catch missing argument error
    	catch(MissingArgumentException e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.initStyle(StageStyle.UTILITY);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error");
    		alert.setContentText("Search must insert first name and family name");
    		alert.showAndWait();
    	}	
    }
    // end searchButtonPressed
    
    // checkContains method
    // check if there is a contact in the list with the name received by user
	private boolean checkContains(Contact contact, String firstName, String familyName) {
		if(contact.getFamilyName().equals(familyName)) {
			if(contact.getFirstName().equals(firstName)) {
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
	// end of checkContains
	
	// displayContact method
	private void displayContact(Contact contact) {
		firstNameTextArea.setText(contact.getFirstName()+"\n");
    	familyNameTextArea.setText(contact.getFamilyName()+"\n");
    	phoneNumberTextArea.setText(contactList.get(contact)+"\n");
	}
	// end of displayContact

	//updateButtonPressed method
	@FXML
    void updateButtonPressed(ActionEvent event) throws Exception{
		int existFlag = 0;
    	try {
	    	firstName = firstNameTextField.getText();
	    	familyName = familyNameTextField.getText();
	    	phoneNumber = phoneNumberTextField.getText();
	    	// must have both first and family name to update
	    	if(firstName.isEmpty() || familyName.isEmpty()) {
	    		throw new MissingArgumentException("Update must insert first name and family name");
	    	}
	    	try {
		    	Iterator<Contact> iterator = contactList.KeyIterator();
				while(iterator.hasNext()) {
					Contact key = (Contact)iterator.next();
					if(checkContains(key, firstName, familyName)) {
						contactList.add(key, phoneNumber);
						displayList();
						existFlag = 1;
					}
				}
				if(existFlag == 0)
					throw new InvalidArgumentException("The contact does not exist.");
	    	}
	    	// display error window if catch argument error
	    	catch(InvalidArgumentException e) {
	    		Alert alert = new Alert(Alert.AlertType.ERROR);
	    		alert.initStyle(StageStyle.UTILITY);
	    		alert.setTitle("Error");
	    		alert.setHeaderText("Error");
	    		alert.setContentText("The contact does not exist.");
	    		alert.showAndWait();
	    	}
    	}
    	// display error window if catch missing argument error
    	catch(MissingArgumentException e) {
    		Alert alert = new Alert(Alert.AlertType.ERROR);
    		alert.initStyle(StageStyle.UTILITY);
    		alert.setTitle("Error");
    		alert.setHeaderText("Error");
    		alert.setContentText("Update must insert first name and family name");
    		alert.showAndWait();
    	}	
    }
	// end of updateButtomPressed method
	
    // diapleyList method
    public void displayList() {
    	firstNameTextArea.clear();
    	familyNameTextArea.clear();
    	phoneNumberTextArea.clear();
    	
    	Iterator<Contact> iterator = contactList.KeyIterator();
    	while(iterator.hasNext()) {
			Contact key = (Contact)iterator.next();
			firstNameTextArea.appendText(key.getFirstName()+"\n");
	    	familyNameTextArea.appendText(key.getFamilyName()+"\n");
	    	phoneNumberTextArea.appendText(contactList.get(key)+"\n");
		}
    }
    // end of displayList

}
