package controller;
import model.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class AdminController
{
	@FXML
	Button quitButton, createUserButton, deleteUserButton, logoutButton, add;
	@FXML
	TextField usernameEntry;
	@FXML
	ListView<User> users = new ListView<User>();
	private ObservableList<User> userList;
	public void start(Stage primaryStage)
	{
		userList = FXCollections.observableArrayList();
		readFile();
		users.setItems(userList);
		users.getSelectionModel().selectFirst();
		quitButton.setOnAction(this::quitProgram);
		logoutButton.setOnAction(event ->
		{
			try
			{
				logout(event);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		});
		createUserButton.setOnAction(this::createUser);
		deleteUserButton.setOnAction(this::deleteUser);
		add.setOnAction(this::add);
	}
	@FXML
	private void createUser(ActionEvent event)
	{
		usernameEntry.setDisable(false);
		add.setDisable(false);
	}
	@FXML
	private void add(ActionEvent event)
	{
		if(!usernameEntry.getText().isEmpty())
		{
			int listLength = 0;
			for(User u : userList)
			{
				if(usernameEntry.getText().trim().equalsIgnoreCase(u.username))
				{
					Alert alert = new Alert(AlertType.INFORMATION);
					alert.setTitle("Duplicate Information");
					alert.setHeaderText("Duplicate username");
					alert.setContentText("That username already exists, please choose a different one");
					alert.showAndWait();
					usernameEntry.setText("");
					usernameEntry.setDisable(true);
					add.setDisable(true);
					return;
				}
				listLength++;
			}
			String tmp = Integer.toString(listLength + 1);
			User newUser = new User(usernameEntry.getText(), tmp);
			userList.add(newUser);
			users.getSelectionModel().select(userList.indexOf(newUser));
			add.setDisable(true);
			usernameEntry.setText("");
			usernameEntry.setDisable(true);
			deleteUserButton.setDisable(false);
			try
			{
				writeToTextFile();
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		else if(usernameEntry.getText().isEmpty())
		{
			usernameEntry.setDisable(true);
			add.setDisable(true);
		}
	}
	@FXML
	private void deleteUser(ActionEvent event)
	{
		User user = users.getSelectionModel().getSelectedItem();
		Alert confirmDelete = new Alert(AlertType.CONFIRMATION);
		confirmDelete.setTitle("Delete Confirmation");
		confirmDelete.setHeaderText("Please Confirm User Deletion");
		confirmDelete.setContentText(
				"Are you sure you want to delete the following user:  " + "''" + user.getName() + "''" + " ?");
		Optional<ButtonType> result = confirmDelete.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			int tmp = userList.indexOf(user);
			userList.remove(user);
			if(!userList.isEmpty())
			{
				users.getSelectionModel().select(tmp);
			}
			if(userList.isEmpty())
			{
				deleteUserButton.setDisable(true);
			}
			try
			{
				writeToTextFile();
			}
			catch(FileNotFoundException e)
			{
				e.printStackTrace();
			}
		}
		if(result.get() == ButtonType.CANCEL)
		{
			deleteUserButton.setDisable(false);
		}
	}
	@FXML
	private void quitProgram(ActionEvent event)
	{
		Stage stage = (Stage)quitButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	private void logout(ActionEvent event) throws IOException
	{
		Stage stage;
		stage = (Stage)logoutButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		VBox root = (VBox)loader.load();
		LoginController controller = loader.getController();
		controller.start(stage);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
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
	public void writeToTextFile() throws FileNotFoundException
	{
		try
		{
			FileWriter writer = new FileWriter("users.txt");
			BufferedWriter bufferedWriter = new BufferedWriter(writer);
			for(User u : userList)
			{
				bufferedWriter.write(u.getName() + "\t" + u.getID());
				bufferedWriter.newLine();
			}
			bufferedWriter.close();
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
