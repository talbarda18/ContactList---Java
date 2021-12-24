/** 
*
* @author  Tal Barda
* @Date   20-12-2021 
*/

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ContactListWindow extends Application {
	public void start(Stage stage) throws Exception{
		Parent root = FXMLLoader.load(getClass().getResource("ContactList2.fxml"));
		Scene scene = new Scene(root);
		stage.setTitle("Contact List");
		stage.setScene(scene);
		stage.show();
	}
	public static void main(String[]args) {
		launch(args);
	}
}
