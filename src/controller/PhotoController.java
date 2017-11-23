package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Optional;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
import model.Photo;
import model.User;
/**
 * @author Steven Benmoha, Colin Ackerley
 *
 */
@SuppressWarnings("serial")
public class PhotoController extends DataPlusButtons
{
	@FXML
	Button addPhotoButton, removePhotoButton, deleteTagButton, saveChangesButton, editPhotoInfoButton, copyPhotoButton,
			movePhotoButton;
	@FXML
	TextField editCaptionTextField, editTagNameTextField, editTagValueTextField, editPhotoDateTextField;
	@FXML
	ListView<Photo> photoList = new ListView<Photo>();
	@FXML
	Label greeting;
	Album current;
	Calendar curTime;
	User u;
	int num;
	// boolean copied;
	/**
	 * @param primaryStage
	 * @param a
	 * @throws FileNotFoundException
	 * 
	 *             Starts the inside-album view and controls functionality
	 */
	public void start(Stage primaryStage, Album a) throws FileNotFoundException
	{
		deleteTagButton.setOnAction(this::deleteTag);
		// copyPhotoButton.setOnAction(this::copyPhoto);
		movePhotoButton.setOnAction(this::movePhoto);
		current = a;
		u = readCurrentUserFile();
		Album.albumPhotos = readCurrentAlbumFile(u, current);
		
		/*
		if (copied == false) {
			String x = readPhoto();
			if (x != null)
			Album.albumPhotos.add(x);
			copied = true;
			copyPhoto.delete();
			}	
		*/
		
		updateList();
		photoList.setItems(Album.realPhotos);
		photoList.getSelectionModel().selectFirst();
		removePhotoButton.setOnAction(arg0 ->
		{
			try
			{
				deletePhoto(arg0);
			}
			catch(FileNotFoundException e2)
			{
			}
		});
		try
		{
			populatePictures();
		}
		catch(FileNotFoundException e1)
		{
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
			}
		});
		saveChangesButton.setOnAction(event ->
		{
			try
			{
				saveChanges(event);
			}
			catch(FileNotFoundException e1)
			{
			}
		});
		quitButton.setOnAction(this::quitProgram);
		editPhotoInfoButton.setOnAction(event ->
		{
			editPhotoInfo(event);
		});
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
			}
		});
		photoList.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Photo>()
		{
			public void changed(ObservableValue<? extends Photo> ov, Photo oldPhoto, Photo newPhoto)
			{
				// photoList.getSelectionModel().getSelectedItem().setCaption(ov.caption);
				// photoList.getSelectionModel().getSelectedItem().setTagName(newPhoto.tag);
				// photoList.getSelectionModel().getSelectedItem().setTagValue(newPhoto.tagValue);
			}
		});
	}
	
	/**
	 * @param e
	 */
	@FXML
	/**
	 * @param ActionEvent
	 */
	private void movePhoto(ActionEvent e)
	{
	}
	
	/*
	private void copyPhoto(ActionEvent e)
	{
		TextInputDialog dialog = new TextInputDialog("");
		dialog.setTitle("Copy Photo");
		dialog.setHeaderText("Album destination");
		dialog.setContentText("Please enter an album to copy this photo to: ");
		Optional<String> name = dialog.showAndWait();
		
		if (name!= null) {
		writeCopy(photoList.getSelectionModel().getSelectedItem().binary, name.get());
		
		}
		}
		
		*/
	
	@FXML
	/**
	 * @param ActionEvent
	 */
	private void deleteTag(ActionEvent e)
	{
		Photo cur = photoList.getSelectionModel().getSelectedItem();
		cur.setTagName("");
		cur.setTagValue("");
	}
	@FXML
	private void saveChanges(ActionEvent event) throws FileNotFoundException
	{
		boolean validDate = false;
		while(true)
		{
			if(editPhotoDateTextField.getText().trim().length() > 10)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Input");
				alert.setHeaderText("Invalid Date");
				alert.setContentText("Enter a date in the form MM/DD/YYYY");
				alert.showAndWait();
				break;
			}
			try
			{
				DateFormat df = new SimpleDateFormat("MM/d/yyyy");
				df.setLenient(false);
				df.parse(editPhotoDateTextField.getText());
			}
			catch(ParseException e)
			{
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Invalid Input");
				alert.setHeaderText("Invalid Date");
				alert.setContentText("Enter a date in the form MM/DD/YYYY");
				alert.showAndWait();
				break;
			}
			validDate = true;
			break;
		}
		if(validDate)
		{
			editCaptionTextField.setDisable(true);
			editTagNameTextField.setDisable(true);
			editTagValueTextField.setDisable(true);
			editPhotoDateTextField.setDisable(true);
			Photo cur = photoList.getSelectionModel().getSelectedItem();
			cur.setCaption(editCaptionTextField.getText());
			cur.setTagName(editTagNameTextField.getText());
			cur.setTagValue(editTagValueTextField.getText());
		}
	}
	/**
	 * @param event
	 * 
	 *            Enables editing of photo info such as captions/tags
	 */
	@FXML
	private void editPhotoInfo(ActionEvent event)
	{
		editCaptionTextField.setDisable(false);
		editTagNameTextField.setDisable(false);
		editTagValueTextField.setDisable(false);
		editPhotoDateTextField.setDisable(false);
	}
	/**
	 * @param event
	 * @throws FileNotFoundException
	 * 
	 *             Deletes photo from listview and removes from observable lists (of
	 *             type Photo and String)
	 */
	@SuppressWarnings("static-access")
	private void deletePhoto(ActionEvent event) throws FileNotFoundException
	{
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Deletion");
		alert.setHeaderText("Confirm Deleting Photo");
		alert.setContentText("Are you sure you want to delete this photo?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			Album.albumPhotos.remove(photoList.getSelectionModel().getSelectedItem().binary);
			updateList();
			Album.realPhotos.remove(photoList.getSelectionModel().getSelectedItem());
			writeCurrentAlbum(u, current, Album.albumPhotos);
			if(Album.realPhotos.isEmpty())
				removePhotoButton.setDisable(true);
		}
	}
	/**
	 * @param event
	 * @param a
	 * @throws IOException
	 * 
	 *             Adds photo to listview
	 */
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
		
		Album.albumPhotos.add(img);
			
		writeCurrentAlbum(u, current, Album.albumPhotos);
		
		
		updateList();
		populatePictures();
	}
	/**
	 * @throws FileNotFoundException
	 * 
	 *             Populates listview with pictures from album
	 */
	@FXML
	private void populatePictures() throws FileNotFoundException
	{		
		
		DropShadow dropShadow = new DropShadow();
		dropShadow.setRadius(5.0);
		dropShadow.setOffsetX(3.0);
		dropShadow.setOffsetY(3.0);
		dropShadow.setColor(Color.color(0.4, 0.5, 0.5));
		photoList.setCellFactory(param -> new ListCell<Photo>()
		{
			private ImageView imageView = new ImageView();
			@Override
			public void updateItem(Photo p, boolean empty)
			{
				super.updateItem(p, empty);
				if(empty)
				{
					setText(null);
					setGraphic(null);
				}
				else
				{
					imageView.setImage(p.image);
					imageView.setFitWidth(50);
					imageView.setFitHeight(50);
					imageView.setEffect(dropShadow);
					setGraphic(imageView);
				}
			}
		});
		photoList.setCursor(Cursor.HAND);
		photoList.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
				{
					if(mouseEvent.getClickCount() == 1)
					{
						Photo cur = photoList.getSelectionModel().getSelectedItem();
						editCaptionTextField.setText(cur.getCaption());
						editTagNameTextField.setText(cur.getTag());
						editTagValueTextField.setText(cur.getTagValue());
						editPhotoDateTextField.setText(cur.getDate());
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
							controller.start(stage, current, photoList.getSelectionModel().getSelectedItem());
							stage.setResizable(true);
							stage.setTitle("Photo Library");
							Scene scene = new Scene(root);
							stage.setScene(scene);
							stage.show();
						}
						catch(IOException e)
						{
						}
					}
				}
				if(mouseEvent.getButton().equals(MouseButton.SECONDARY))
				{
				}
			}
		});
	}
	/**
	 * @throws FileNotFoundException
	 * 
	 *             Creates observable list of photos from observable list of
	 *             strings. a.albumPhotos is list of string representation of images
	 *             and this method turns them into Photo objects
	 */
	
	public void updateList() throws FileNotFoundException
	{
		Album.realPhotos.clear();
		int num = 0;
		
		
		
		for(String i : Album.albumPhotos)
		{
			decoder(i, ("src\\model\\userdata\\albums\\" + u.getName() + "\\" + current.getName()),
					("src\\model\\userdata\\albums\\" + u.getName() + "\\" + current.getName() + "\\-" + num + ".png"),
					num);
			String path = "src\\model\\userdata\\albums\\" + u.getName() + "\\" + current.getName() + "\\-" + num
					+ ".png";
			Image image = new Image(new FileInputStream(path));
			Photo photo = new Photo(image, num, i);
			curTime = Calendar.getInstance();
			curTime.set(Calendar.MILLISECOND, 0);
			photo.setDate(curTime);			
			Album.realPhotos.add(photo);
			num++;
		}
		
	}
	/**
	 * @param photo
	 * 
	 *            Sets the photo attributes from editable textfields
	 */
}