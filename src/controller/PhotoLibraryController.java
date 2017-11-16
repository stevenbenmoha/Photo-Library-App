package controller;
import java.io.IOException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.User;
public class PhotoLibraryController
{
	@FXML
	Button quitButton, logoutButton, addAlbumButton, deleteAlbumButton, renameButton, searchButton;
	@FXML
	AnchorPane optionsPanel, albumsDisplayPanel, detailsPanel;
	@FXML
	Label greeting;
	ObservableList<Album> photoLibrary = FXCollections.observableArrayList();
	public void start(Stage primaryStage, User u)
	{
		greeting.setText(u.username + greeting.getText());
		quitButton.setOnAction(this::quitProgram);
		addAlbumButton.setOnAction(this::addAlbum);
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
		searchButton.setOnAction(event ->
		{
			try
			{
				search(event);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		});
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
	@FXML
	private void search(ActionEvent event) throws IOException
	{
		Stage stage;
		stage = (Stage)searchButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/search.fxml"));
		VBox root = (VBox)loader.load();
		SearchController controller = loader.getController();
		controller.start(stage);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	@FXML
	private void addAlbum(ActionEvent event)
	{
		while(true)
		{
			TextInputDialog dialog = new TextInputDialog("");
			dialog.setTitle("Create New Album");
			dialog.setHeaderText("New Photo Album");
			dialog.setContentText("Please enter a name for your photo album:");
			Optional<String> name = dialog.showAndWait();
			if(name.get().trim().equals(""))
			{
				dialog.close();
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Input");
				alert.setHeaderText("Invalid Name");
				alert.setContentText("Album Names Can't Be Blank");
				alert.showAndWait();
				continue;
			}
		}
	}
}
