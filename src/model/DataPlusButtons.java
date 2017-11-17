package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import controller.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DataPlusButtons implements Serializable {
	
	public static final String photos71 = "photos71";
	public static final String src = "src";
	public static final String model = "model";
	public static final String userListFile = "userListFile.dat";
	public static final String photoLibraryFile = "photoLibraryFile.dat";
			
	public static File albumFile = new File("src\\model\\photoLibraryFile.dat");
	public static File userFile = new File("src\\model\\userListFile.dat");
	
	@FXML
	protected Button quitButton, logoutButton, searchButton, returnToAlbumsButton;
	
	protected static ObservableList<User> userList = FXCollections.observableArrayList();
	
	protected static ObservableList<Album> photoLibrary = FXCollections.observableArrayList();
	
		
	@FXML
	protected void quitProgram(ActionEvent event)
	{
		Stage stage = (Stage)quitButton.getScene().getWindow();
		stage.close();
	}
	@FXML
	protected void logout(ActionEvent event) throws IOException
	{
		Stage stage;
		stage = (Stage)logoutButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/login.fxml"));
		VBox root = (VBox)loader.load();
		LoginController controller = loader.getController();
		controller.start(stage);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	@FXML
	protected void search(ActionEvent event) throws IOException
	{
		Stage stage;
		stage = (Stage)searchButton.getScene().getWindow();
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/search.fxml"));
		VBox root = (VBox)loader.load();
		SearchController controller = loader.getController();
		controller.start(stage);
		stage.setResizable(true);
		stage.setTitle("Photo Library");
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	

	@FXML
	protected void returnToAlbums(ActionEvent event) throws IOException {
		
	  Stage stage;
	  stage = (Stage)logoutButton.getScene().getWindow(); 
	  FXMLLoader loader = new FXMLLoader();
	  loader.setLocation(getClass().getResource("/view/main.fxml"));
	  VBox root = (VBox)loader.load();
	  PhotoLibraryController controller = loader.getController();
	  controller.start(stage);
	  stage.setResizable(true);
	  stage.setTitle("Photo Library"); Scene scene = new Scene(root);
	  stage.setScene(scene); stage.show();
	  }

	

	
	public static ObservableList<User> readUserFile() {
		
		if(userFile.exists()) {
		
        try {
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(src+ File.separator+ model + File.separator + userListFile));
            ArrayList<User> userList = (ArrayList<User>) ois.readObject();
            return FXCollections.observableList(userList);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
        return FXCollections.observableList(userList);
        
    }
	
	public static void writeUser(ObservableList<User> userList) {
	 try {
		 	
		 	userFile.createNewFile(); // if file already exists will do nothing 
		 	FileOutputStream output = new FileOutputStream(userFile, false);
		 
            // write object to files
            ObjectOutputStream oos = new ObjectOutputStream(output);
            oos.writeObject(new ArrayList<User>(userList));
            oos.close();


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
	
}
	
	
	public static ObservableList<Album> readAlbumFile() {
		
		if (albumFile.exists()) {
		
		 try {
            
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(src+ File.separator+ model + File.separator + photoLibraryFile));
            ArrayList<Album> photoLibrary = (ArrayList<Album>) ois.readObject();
            return FXCollections.observableList(photoLibrary);
            
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
		}
		
        return FXCollections.observableList(photoLibrary);
    }
	
	public static void writeAlbum(ObservableList<Album> photoLibrary) {
		 try {
			 
			 		 
			 	albumFile.createNewFile(); // if file already exists will do nothing 
			 	FileOutputStream output = new FileOutputStream(albumFile, false);
			 
	            // write object to files
	            ObjectOutputStream oos = new ObjectOutputStream(output);
	            oos.writeObject(new ArrayList<Album>(photoLibrary));
	            oos.close();


	        } catch (FileNotFoundException e) {
	            e.printStackTrace();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
		
	}
	
	
	
	
}