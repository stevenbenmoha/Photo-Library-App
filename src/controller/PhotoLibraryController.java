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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.*;


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
		// greeting.setText(u.username + greeting.getText());
		photoLibrary = readAlbumFile();
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
				Album album = new Album(name.get());
				photoLibrary.add(album);
				writeAlbum(photoLibrary);
				break;
			}
			populateAlbums();
		}
		catch(NoSuchElementException e)
		{
		}
		
		}
	
	@FXML
	private void populateAlbums() {
			
		tileDisplay.getChildren().clear();
			
		 String path = "src"+ "/model" + "/folder_img.png"; ;
		 
		 tileDisplay.setPadding(new Insets(15, 15, 15, 15));
	     tileDisplay.setHgap(15);
	     tileDisplay.setVgap(15);
		
		File image = new File(path);
        

        for (Album a : photoLibrary) {
                ImageView imageView;
                imageView = createImageView(image);
                tileDisplay.getChildren().addAll(imageView);
        }

		
	}
	
	public ImageView createImageView(final File imageFile) {
   
        ImageView imageView = null;
        try {
            final Image image = new Image(new FileInputStream(imageFile), 100, 0, true,
                    true);
            imageView = new ImageView(image);
            imageView.setFitWidth(100);
        }
         catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        return imageView;
    }
	
	
}
