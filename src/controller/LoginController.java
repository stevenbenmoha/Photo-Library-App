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
public class LoginController
{
	@FXML
	Button quitButton, loginButton;
	@FXML
	TextField usernameTextField;
	private ObservableList<User> userList;
	public void start(Stage primaryStage)
	{
		userList = FXCollections.observableArrayList();
		readFile();
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
				controller.start(stage, u);
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
	private void readFile()
	{
		Scanner scan = null;
		try
		{
			scan = new Scanner(new File("users.txt"));
		}
		// Make sure a file exists and a lack of file won't crash the program
		catch(FileNotFoundException e)
		{
			File file = new File("users.txt");
			try
			{
				file.createNewFile();
			}
			catch(IOException e1)
			{
				e1.printStackTrace();
			}
			return;
		}
		while(scan.hasNextLine())
		{
			String curLine = scan.nextLine();
			String[] splitted = curLine.split("\t");
			String username = splitted[0].trim();
			String photoLibraryID = splitted[1].trim();
			User u = new User(username, photoLibraryID);
			userList.add(u);
		}
		scan.close();
	}
}