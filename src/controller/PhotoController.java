package controller;

import java.io.File;
import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.DataPlusButtons;

public class PhotoController extends DataPlusButtons {

	@FXML
	Button addPhotoButton, addEditCaptionButton, removePhotoButton, tagPhotoButton, deleteTagButton;
	@FXML
	TilePane tileDisplay;
	@FXML
	Label greeting;

	Album current;

	public void start(Stage primaryStage, Album a) {

		current = a;
		u = readCurrentUserFile();
		// a.albumPhotos = readCurrentAlbumFile(u, a);
		populatePictures();

		greeting.setText(greeting.getText() + " " + a.albumName);

		addPhotoButton.setOnAction((ActionEvent event) -> {

			addPhoto(event, a);

		});

		quitButton.setOnAction(this::quitProgram);
		logoutButton.setOnAction(event -> {
			try {
				logout(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});

		returnToAlbumsButton.setOnAction(event -> {
			try {
				returnToAlbums(event);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private void addPhoto(ActionEvent event, Album a) {

		Stage stage;
		stage = (Stage) logoutButton.getScene().getWindow();

		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Photo");
		File file = fileChooser.showOpenDialog(stage);
		
		if (file == null) {
			
		 return;
		}
		

		Image image = new Image(file.toURI().toString());

		// Photo photo = new Photo();

		a.albumPhotos.add(image);
		// writeCurrentAlbum(u,a,a.albumPhotos);
		populatePictures();

	}

	@FXML
	private void populatePictures() {   // same as populateAlbums() but with pictures

		tileDisplay.getChildren().clear();

		tileDisplay.setPadding(new Insets(15, 15, 15, 15));
		tileDisplay.setHgap(15);
		tileDisplay.setVgap(15);

		for (Image i : a.albumPhotos) { // iterate through the album's ObservableList of images
			ImageView imageView;
			imageView = createImageView(i, current);
			tileDisplay.getChildren().addAll(imageView);
		}

	}

	public ImageView createImageView(Image i, Album current) {

		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));

		ImageView imageView = null;
		imageView = new ImageView(i);
		imageView.setFitWidth(100);
		imageView.setFitHeight(100);
		imageView.setEffect(dropShadow);
		imageView.setCursor(Cursor.HAND);
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {

				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {

					if (mouseEvent.getClickCount() == 2) {

						try {

							Stage stage;
							stage = (Stage) logoutButton.getScene().getWindow();		// If you double click a picture, it will
							FXMLLoader loader = new FXMLLoader();						// bring you to a larger pic display screen
							loader.setLocation(getClass().getResource("/view/photofull.fxml"));
							VBox root = (VBox) loader.load();
							PhotoDisplayController controller = loader.getController();
							controller.start(stage, current, i);
							stage.setResizable(true);
							stage.setTitle("Photo Library");
							Scene scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
						}

						catch (IOException e) {

							e.printStackTrace();
						}

					}
				}

				if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) { 

				}
			}
		});
		return imageView;
	}

}
