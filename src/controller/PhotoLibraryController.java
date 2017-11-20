package controller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Optional;
import com.sun.prism.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Album;
import model.DataPlusButtons;
public class PhotoLibraryController extends DataPlusButtons
{
	@FXML
	Button addAlbumButton, deleteAlbumButton, renameButton, searchButton;
	@FXML
	AnchorPane optionsPanel, albumsDisplayPanel, detailsPanel;
	@FXML
	Label greeting;
	@FXML
	TilePane tileDisplay;
	public void start(Stage primaryStage)
	{
		u = readCurrentUserFile();
		greeting.setText(u.username + greeting.getText());
		u.userPhotoLibrary = readUsersAlbumsFile(u);
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
		catch(NoSuchElementException e)
		{
		}
	}
	@FXML
	private void populateAlbums()
	{
		tileDisplay.getChildren().clear();
		String path = "src" + "/model" + "/folder_img.png";
		;
		tileDisplay.setPadding(new Insets(15, 15, 15, 15));
		tileDisplay.setHgap(15);
		tileDisplay.setVgap(15);
		File image = new File(path);
		for(Album a : u.userPhotoLibrary)
		{
			ImageView imageView;
			imageView = createImageView(image, a);
			tileDisplay.getChildren().addAll(imageView);
		}
	}
	public ImageView createImageView(final File imageFile, Album a)
	{
		ImageView imageView = null;
		try
		{
			final Image image = new Image(new FileInputStream(imageFile), 100, 0, true, true);
			imageView = new ImageView(image);
			imageView.setFitWidth(100);
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
						}
						if(mouseEvent.getClickCount() == 2)
						{
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
		catch(FileNotFoundException ex)
		{
			ex.printStackTrace();
		}
		return imageView;
	}
}
