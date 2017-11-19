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

public class PhotoDisplayController extends DataPlusButtons {

	@FXML
	ImageView bigPicture;
	@FXML
	Button returnToAlbumButton;

	public void start(Stage primaryStage, Album current, Image currentImg) {

		bigPicture.setImage(currentImg);
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

	}

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

}