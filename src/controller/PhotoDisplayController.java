package controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;

/**
 * @author Steven Benmoha, Colin Ackerley
 *
 */
public class PhotoDisplayController extends DataPlusButtons {
	@FXML
	ImageView bigPicture;
	@FXML
	Button returnToAlbumButton, previousPicture, nextPicture;

	Photo pic;

	int length = a.realPhotos.size();

	/**
	 * @param primaryStage
	 * @param current
	 * @param p
	 * 
	 * Starts the fullscreen display and controls functionality
	 */
	public void start(Stage primaryStage, Album current, Photo p) {

		pic = p;
		bigPicture.setImage(p.image);
		quitButton.setOnAction(this::quitProgram);
		logoutButton.setOnAction(event -> {
			try {
				logout(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		returnToAlbumButton.setOnAction(event -> {
			try {
				returnToCurrentAlbum(event, current);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		
		nextPicture.setOnAction(event -> {
			pic = nextPic(event);			
			
		});
		previousPicture.setOnAction(event -> {
			pic = prevPic(event);			
		});

			
		
	}

	/**
	 * @param event
	 * @param current
	 * @throws IOException
	 * 
	 * returns to current album that picture originates from
	 */
	protected void returnToCurrentAlbum(ActionEvent event, Album current) throws IOException {
		Stage stage;
		stage = (Stage) logoutButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/photo.fxml"));
		VBox root = (VBox) loader.load();
		PhotoController controller = loader.getController();
		controller.start(stage, current);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}

	/**
	 * @param event
	 * @return
	 * 
	 * Goes to next pic in album
	 */
	@FXML
	public Photo nextPic(ActionEvent event) {

		if ((a.realPhotos.indexOf(pic) + 1) == length) {
			
			return pic;
		}

		else {

			int newIndex = a.realPhotos.indexOf(pic) + 1;

			bigPicture.setImage(a.realPhotos.get(newIndex).image);

			return a.realPhotos.get(newIndex);
		}
	}

	/**
	 * @param event
	 * @return
	 * 
	 * Goes to previous pic in album
	 */
	@FXML
	public Photo prevPic(ActionEvent event) {

		if ((a.realPhotos.indexOf(pic)) == 0) {
			
			return pic;
		}

		else {

			int newIndex = a.realPhotos.indexOf(pic) - 1;

			bigPicture.setImage(a.realPhotos.get(newIndex).image);

			return a.realPhotos.get(newIndex);

		}

	}
}