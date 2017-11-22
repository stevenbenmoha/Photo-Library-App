package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Calendar;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import model.Album;
import model.DataPlusButtons;
import model.User;
public class PhotoController extends DataPlusButtons
{
	@FXML
	Button addPhotoButton, removePhotoButton, deleteTagButton, saveChangesButton, editPhotoInfoButton;
	@FXML
	TextField editCaptionTextField, editTagNameTextField, editTagValueTextField, editPhotoDateTextField;
	@FXML
	ListView<String> photoList = new ListView<String>();
	@FXML
	Label greeting;
	Album current;
	Calendar curTime;
	User u;
	@SuppressWarnings("static-access")
	public void start(Stage primaryStage, Album a)
	{
		current = a;
		u = readCurrentUserFile();
		a.albumPhotos = readCurrentAlbumFile(u, current);
		removePhotoButton.setOnAction(this::deletePhoto);
		// writeCurrentAlbum(u,current,a.albumPhotos);
		photoList.setItems(a.albumPhotos);
		try
		{
			populatePictures();
		}
		catch(FileNotFoundException e1)
		{
			e1.printStackTrace();
		}
		greeting.setText(greeting.getText() + " " + a.albumName);
		addPhotoButton.setOnAction((ActionEvent event) ->
		{
			try
			{
				addPhoto(event, a);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		});
		saveChangesButton.setOnAction(this::saveChanges);
		quitButton.setOnAction(this::quitProgram);
		editPhotoInfoButton.setOnAction(this::editPhotoInfo);
		editCaptionTextField.setDisable(true);
		editTagNameTextField.setDisable(true);
		editTagValueTextField.setDisable(true);
		editPhotoDateTextField.setDisable(true);
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
	private void saveChanges(ActionEvent event)
	{
		editCaptionTextField.setDisable(true);
		editTagNameTextField.setDisable(true);
		editTagValueTextField.setDisable(true);
		editPhotoDateTextField.setDisable(true);
	}
	@SuppressWarnings("static-access")
	private void deletePhoto(ActionEvent event)
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Deletion");
		alert.setHeaderText("Confirm Deleting Photo");
		alert.setContentText("Are you sure you want to delete this photo?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			a.albumPhotos.remove(photoList.getSelectionModel().getSelectedItem());
			if(a.albumPhotos.isEmpty())
				removePhotoButton.setDisable(true);
		}
	}
	@FXML
	private void editPhotoInfo(ActionEvent event)
	{
		editCaptionTextField.setDisable(false);
		editTagNameTextField.setDisable(false);
		editTagValueTextField.setDisable(false);
		editPhotoDateTextField.setDisable(false);
	}
	@SuppressWarnings("static-access")
	private void addPhoto(ActionEvent event, Album a) throws IOException
	{
		Stage stage;
		stage = (Stage)logoutButton.getScene().getWindow();
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Choose Photo");
		File file = fileChooser.showOpenDialog(stage);
		if(file == null)
		{
			return;
		}
		String path = file.getAbsolutePath();
		path.substring(1);
		String img = encoder(path);
		curTime = Calendar.getInstance();
		curTime.set(Calendar.MILLISECOND, 0);
		// Photo photo = new Photo();
		a.albumPhotos.add(img);
		writeCurrentAlbum(u, current, a.albumPhotos);
		populatePictures();
	}
	@FXML
	private void populatePictures() throws FileNotFoundException
	{ // same as populateAlbums() but with pictures
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		int num = 0;
		for(String i : current.albumPhotos)
		{
			// ImageView imageView;
			decoder(i, ("src\\model\\userdata\\albums\\" + current.getName()),
					("src\\model\\userdata\\albums\\" + current.getName() + "\\-" + num + ".png"), num);
			// imageView = createImageView(i, current, num);
			num++;
		}
		photoList.setCellFactory(param -> new ListCell<String>()
		{
			private ImageView imageView = new ImageView();
			@Override
			public void updateItem(String j, boolean empty)
			{
				super.updateItem(j, empty);
				if(empty)
				{
					setText(null);
					setGraphic(null);
				}
				else
				{
					int num2 = 0;
					for(String i : current.albumPhotos)
					{
						String path = "src\\model\\userdata\\albums\\" + current.getName() + "\\-" + num2 + ".png";
						Image image;
						try
						{
							image = new Image(new FileInputStream(path));
							imageView.setImage(image);
							imageView.setFitWidth(50);
							imageView.setFitHeight(50);
							imageView.setEffect(dropShadow);
							setGraphic(imageView);
							num2++;
						}
						catch(FileNotFoundException e)
						{
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
		});
	}
	public ImageView createImageView(String i, Album current, int num) throws FileNotFoundException
	{
		String path = "src\\model\\userdata\\albums\\" + current.getName() + "\\-" + num + ".png";
		final Image image = new Image(new FileInputStream(path));
		final ImageView imageView = new ImageView(image);
		imageView.setCursor(Cursor.HAND);
		imageView.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
				{
					if(mouseEvent.getClickCount() == 1)
					{
						imageView.setEffect(new DropShadow(200, Color.SEAGREEN));
					}
					if(mouseEvent.getClickCount() == 2)
					{
						try
						{
							Stage stage;
							stage = (Stage)logoutButton.getScene().getWindow(); // If you double click a picture, it
																				// will
							FXMLLoader loader = new FXMLLoader(); // bring you to a larger pic display screen
							loader.setLocation(getClass().getResource("/view/photofull.fxml"));
							VBox root = (VBox)loader.load();
							PhotoDisplayController controller = loader.getController();
							controller.start(stage, current, image);
							stage.setResizable(true);
							stage.setTitle("Photo Library");
							Scene scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
					}
				}
				if(mouseEvent.getButton().equals(MouseButton.SECONDARY))
				{
				}
			}
		});
		return imageView;
	}
	public Image createImage()
	{
		return null;
	}
}