package controller;
import model.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class LoginController extends DataPlusButtons
{
	@FXML
	Button loginButton;
	@FXML
	TextField usernameTextField;
	

	public void start(Stage primaryStage)
	{	
		
		userList = readUserFile();
		usernameTextField.setEditable(true);
		quitButton.setOnAction(this::quitProgram);
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
	@FXML
	private void goToDestination(ActionEvent event) throws IOException
	{
		Stage stage;
		boolean valid = false;
		// Parent root;
		if(usernameTextField.getText().equals("admin"))
		{
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
			{
				valid = true;
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
	@FXML
	public void quitProgram(ActionEvent event)
	{
		// Save progress first, then
		Stage stage = (Stage)quitButton.getScene().getWindow();
		stage.close();
	}
	
}