package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
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

public class PhotoLibraryController extends DataPlusButtons
{
	@FXML
	Button addAlbumButton, deleteAlbumButton, renameButton, searchButton;
	@FXML
	AnchorPane optionsPanel, albumsDisplayPanel, detailsPanel;
	@FXML
	Label greeting;
	@FXML
	ListView<Album> albumList = new ListView<Album>();
	
	User u;
	
	public void start(Stage primaryStage) throws FileNotFoundException
	{	
		
		u = readCurrentUserFile();
		greeting.setText(u.username + greeting.getText());
		u.userPhotoLibrary = readUsersAlbumsFile(u);
		
		
		albumList.setItems(u.userPhotoLibrary);		
		
		populateAlbums();
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
				for(Album a : u.userPhotoLibrary)
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
				u.userPhotoLibrary.add(album);
				writeUsersAlbums(u, u.userPhotoLibrary);
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
				if(mouseEvent.getButton().equals(MouseButton.PRIMARY))
				{
					if(mouseEvent.getClickCount() == 1)
					{
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
							e.printStackTrace();
						}
					}
				}
				if(mouseEvent.getButton().equals(MouseButton.SECONDARY))
				{
				}
			}
		});
		
		
	}
}
