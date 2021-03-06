


package controller;
import java.io.IOException;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.DataPlusButtons;
import model.User;
/**
 * @author Steven Benmoha, Colin Ackerley
 *
 */
public class AdminController extends DataPlusButtons
{
	@FXML
	Button createUserButton, deleteUserButton, add;
	@FXML
	TextField usernameEntry;
	@FXML
	ListView<User> users = new ListView<User>();
	/**
	 * @param primaryStage
	 * @throws IOException
	 * 
	 * the start method that runs the controller
	 */
	public void start(Stage primaryStage) throws IOException
	{
		userList = readUserFile(); // Deserializes .dat file contained in model\\userdata\\users and updates list
									// of users
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
	/**
	 * @param event
	 * 
	 * Enables the create user button
	 */
	@FXML
	private void createUser(ActionEvent event)
	{
		usernameEntry.setDisable(false);
		add.setDisable(false);
	}
	/**
	 * @param event
	 * 
	 * Adds the user to the userlist
	 */
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
			writeUser(userList);
		}
		else if(usernameEntry.getText().isEmpty())
		{
			usernameEntry.setDisable(true);
			add.setDisable(true);
		}
	}
	/**
	 * @param event
	 * 
	 * Deletes user from user list
	 */
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
			writeUser(userList);
		}
		if(result.get() == ButtonType.CANCEL)
		{
			deleteUserButton.setDisable(false);
		}
	}
}