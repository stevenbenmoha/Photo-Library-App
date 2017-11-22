package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.DataPlusButtons;
import model.User;
@SuppressWarnings("serial")
public class PhotoLibraryController extends DataPlusButtons
{
	@FXML
	Button addAlbumButton, deleteAlbumButton, editAlbumButton, searchButton;
	@FXML
	AnchorPane optionsPanel, albumsDisplayPanel, detailsPanel;
	@FXML
	Label greeting;
	@FXML
	TextField albumInfo;
	@FXML
	ListView<Album> albumList = new ListView<Album>();
	User u;
	public void start(Stage primaryStage) throws FileNotFoundException
	{
		u = readCurrentUserFile();
		primaryStage.setHeight(625);
		primaryStage.setWidth(900);
		primaryStage.setMaxHeight(625);
		primaryStage.setMaxWidth(900);
		greeting.setText(u.username + greeting.getText());
		deleteAlbumButton.setDisable(true);
		User.userPhotoLibrary = readUsersAlbumsFile(u);
		albumList.setItems(User.userPhotoLibrary);
		populateAlbums();
		quitButton.setOnAction(this::quitProgram);
		addAlbumButton.setOnAction(this::addAlbum);
		editAlbumButton.setOnAction(this::editAlbumName);
		deleteAlbumButton.setOnAction(this::deleteAlbum);
		albumInfo.setDisable(true);
		editAlbumButton.setDisable(true);
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
	private void deleteAlbum(ActionEvent e)
	{
		Album cur = albumList.getSelectionModel().getSelectedItem();
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Confirm Deletion");
		alert.setHeaderText("Confirm Deleting Album");
		alert.setContentText("Are you sure you want to delete " + cur.getName() + "?");
		Optional<ButtonType> result = alert.showAndWait();
		if(result.get() == ButtonType.OK)
		{
			User.userPhotoLibrary.remove(cur);
			writeUsersAlbums(u, User.userPhotoLibrary);
			if(User.userPhotoLibrary.isEmpty())
				deleteAlbumButton.setDisable(true);
		}
	}
	@FXML
	private void editAlbumName(ActionEvent e)
	{
		try
		{
			Album cur = albumList.getSelectionModel().getSelectedItem();
			while(true)
			{
				TextInputDialog dialog = new TextInputDialog("");
				dialog.setTitle("Rename Album");
				dialog.setHeaderText("Rename Album");
				dialog.setContentText("Please enter a new name for your photo album:");
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
				for(Album a : User.userPhotoLibrary)
				{
					if(name.get().trim().equals(a.albumName))
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Duplicate Information");
						alert.setHeaderText("Duplicate album name");
						alert.setContentText("That album name already exists, please choose a different one");
						alert.showAndWait();
						return;
					}
				}
				cur.setName(name.get());
				albumInfo.setText(name.get());
				writeUsersAlbums(u, User.userPhotoLibrary);
				break;
			}
			populateAlbums();
		}
		catch(NoSuchElementException | FileNotFoundException no)
		{
		}
	}
	@FXML
	private void addAlbum(ActionEvent event)
	{
		try
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
				for(Album a : User.userPhotoLibrary)
				{
					if(name.get().trim().equals(a.albumName))
					{
						Alert alert = new Alert(AlertType.INFORMATION);
						alert.setTitle("Duplicate Information");
						alert.setHeaderText("Duplicate album name");
						alert.setContentText("That album name already exists, please choose a different one");
						alert.showAndWait();
						return;
					}
				}
				Album album = new Album(name.get());
				User.userPhotoLibrary.add(album);
				writeUsersAlbums(u, User.userPhotoLibrary);
				break;
			}
			populateAlbums();
		}
		catch(NoSuchElementException | FileNotFoundException e)
		{
		}
	}
	@FXML
	private void populateAlbums() throws FileNotFoundException
	{
		String path = "src" + "/model" + "/folder_img.png";
		File file = new File(path);
		final Image folderIcon;
		folderIcon = new Image(new FileInputStream(file), 75, 0, true, true);
		albumList.setCellFactory(param -> new ListCell<Album>()
		{
			private ImageView imageView = new ImageView();
			@Override
			public void updateItem(Album a, boolean empty)
			{
				super.updateItem(a, empty);
				if(empty)
				{
					setText(null);
					setGraphic(null);
				}
				else
				{
					imageView.setImage(folderIcon);
					setText(a.albumName);
					setGraphic(imageView);
				}
			}
		});
		albumList.setCursor(Cursor.HAND);
		albumList.setOnMouseClicked(new EventHandler<MouseEvent>()
		{
			@Override
			public void handle(MouseEvent mouseEvent)
			{
				try
				{
					if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
					{
						if(mouseEvent.getClickCount() == 1)
						{
							editAlbumButton.setDisable(false);
							deleteAlbumButton.setDisable(false);
							Album cur = albumList.getSelectionModel().getSelectedItem();
							albumInfo.setText(cur.getName());
						}
						if(mouseEvent.getClickCount() == 2)
						{
							Album a = albumList.getSelectionModel().getSelectedItem();
							// writeCurrentAlbum(u,a, a.albumPhotos);
							try
							{
								Stage stage;
								stage = (Stage)logoutButton.getScene().getWindow();
								FXMLLoader loader = new FXMLLoader();
								loader.setLocation(getClass().getResource("/view/photo.fxml"));
								VBox root = (VBox)loader.load();
								PhotoController controller = loader.getController();
								controller.start(stage, a);
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
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}
		});
	}
}
