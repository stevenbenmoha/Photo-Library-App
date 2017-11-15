package controller;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
public class SearchController
{
	@FXML
	Button quitButton, logoutButton, returnToAlbumsButton, createAlbumFromResultsButton, searchDateButton,
			searchTagButton;
	@FXML
	TextField searchTextField;
	@FXML
	AnchorPane searchPanel, resultsPanel;
	public void start(Stage primaryStage)
	{
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
		/*
		 * returnToAlbumsButton.setOnAction(event -> { try { returnToAlbums(event);
		 * 
		 * } catch (IOException e) { e.printStackTrace(); } });
		 */
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
	/*
	 * 
	 * @FXML private void returnToAlbums(ActionEvent event) throws IOException {
	 * Stage stage;
	 * 
	 * stage = (Stage) logoutButton.getScene().getWindow(); FXMLLoader loader = new
	 * FXMLLoader(); loader.setLocation(getClass().getResource("/view/main.fxml"));
	 * VBox root = (VBox) loader.load(); PhotoLibraryController controller =
	 * loader.getController(); controller.start(stage,); stage.setResizable(true);
	 * stage.setTitle("Photo Library"); Scene scene = new Scene(root);
	 * stage.setScene(scene); stage.show(); }
	 * 
	 */
}
