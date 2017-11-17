package controller;
import java.awt.ScrollPane;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.*;
import controller.*;

public class PhotoController extends DataPlusButtons {
	
	@FXML	
	Button addPhotoButton, addEditCaptionButton, removePhotoButton, tagPhotoButton, deleteTagButton;
	@FXML
	TilePane tileDisplay;
	@FXML
	Label greeting;
		
	final FileChooser fileChooser = new FileChooser();
	
	
	public void start(Stage primaryStage, Album a)
	{
		
		u = readCurrentUserFile();
		a.albumPhotos = readCurrentAlbumFile(u,a);
		
		greeting.setText(greeting.getText()+" "+ a.albumName);
		
		
		addPhotoButton.setOnAction((ActionEvent event) -> {
			
			addPhoto(event);				
            
		});
		
		
		
		
		
		
		
		
		
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
	
	@FXML
	private void populatePictures() {

		tileDisplay.getChildren().clear();
		
		tileDisplay.setPadding(new Insets(15, 15, 15, 15));
		tileDisplay.setHgap(15);
		tileDisplay.setVgap(15);
		
		
		
	}
	
	@FXML
	private void addPhoto(ActionEvent event) {
		
		Stage stage;
		stage = (Stage)logoutButton.getScene().getWindow();
		
        File file = fileChooser.showOpenDialog(stage);
        Image photo = new Image("file");
        
     	
	}
	
	
		
	
}
