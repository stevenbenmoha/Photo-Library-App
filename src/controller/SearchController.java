package controller;
import java.io.IOException;
import java.io.Serializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;
public class SearchController extends DataPlusButtons
{
	@FXML
	Button createAlbumFromResultsButton, searchDateButton, searchTagButton;
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
		returnToAlbumsButton.setOnAction(event ->
		{
			try
			{
				returnToAlbums(event);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		});
	}
}
