package controller;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.DataPlusButtons;
import model.User;
/**
 * @author Steven Benmoha, Colin Ackerley
 *
 */
@SuppressWarnings("serial")
public class LoginController extends DataPlusButtons
{
	@FXML
	Button loginButton;
	@FXML
	TextField usernameTextField;
	/**
	 * @param primaryStage
	 * 
	 * Starts the login page and controls login functionality
	 */
	public void start(Stage primaryStage)
	{
		userList = readUserFile(); // Deserializes .dat file contained in model\\userdata\\users and updates list
									// of users
		usernameTextField.setEditable(true);
		quitButton.setOnAction(this::quitProgram);
		primaryStage.setMaxHeight(625);
		primaryStage.setMaxWidth(900);
		loginButton.setOnAction(event ->
		{
			try
			{
				goToDestination(event);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		});
	}
	/**
	 * @param event
	 * @throws IOException
	 * 
	 * Goes to admin page or album page depending on login username
	 * 
	 */
	@FXML
	private void goToDestination(ActionEvent event) throws IOException
	{
		Stage stage;
		boolean valid = false;
		// Parent root;
		if(usernameTextField.getText().equals("admin"))
		{ // Checks if admin is logging in
			valid = true;
			stage = (Stage)loginButton.getScene().getWindow();
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("/view/admin.fxml"));
			VBox root = (VBox)loader.load();
			AdminController controller = loader.getController();
			controller.start(stage);
			stage.setResizable(true);
			stage.setTitle("Photo Library");
			Scene scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		for(User u : userList)
		{
			if(usernameTextField.getText().equals(u.username))
			{ // Checks if username is in the array
				valid = true;
				writeCurrentUser(u);
				stage = (Stage)loginButton.getScene().getWindow();
				FXMLLoader loader = new FXMLLoader();
				loader.setLocation(getClass().getResource("/view/main.fxml"));
				VBox root = (VBox)loader.load();
				PhotoLibraryController controller = loader.getController();
				controller.start(stage);
				stage.setResizable(true);
				stage.setTitle("Photo Library");
				Scene scene = new Scene(root);
				stage.setScene(scene);
				stage.show();
			}
		}
		if(!valid)
		{
			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("Login failed");
			alert.setHeaderText("Login failed");
			alert.setContentText("You have entered an invalid username, please try again");
			alert.showAndWait();
			usernameTextField.setText("");
		}
	}
	/** 
	 * @param event
	 * 
	 * quits the program
	 */
	@FXML
	public void quitProgram(ActionEvent event)
	{
		Stage stage = (Stage)quitButton.getScene().getWindow();
		stage.close();
	}
}